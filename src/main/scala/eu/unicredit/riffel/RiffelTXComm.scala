/* Copyright 2015 UniCredit S.p.A.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package eu.unicredit.riffel

import Chisel._

case class RiffelTXComm(C_PCI_DATA_WIDTH: Int, buffer_size: Int) extends Module {
	val io = new Bundle  {
    	val CLK = Bool(INPUT)
    	val RST = Bool(INPUT)
    
        val TX_CLK = Bool(OUTPUT)
    	val TX = Bool(OUTPUT) 
    	val TX_ACK = Bool(INPUT)
    	val TX_LAST = Bool(OUTPUT)
    	val TX_LEN = UInt(OUTPUT, width=32)
    	val TX_OFF = UInt(OUTPUT, width=31)
    	val TX_DATA = UInt(OUTPUT, width=C_PCI_DATA_WIDTH)
    	val TX_DATA_VALID = Bool(OUTPUT)
    	val TX_DATA_REN = Bool(INPUT)

    	val data_valid = Bool(INPUT)
    	val data_len = UInt(INPUT, width=32)
    	val data = UInt(INPUT, width=C_PCI_DATA_WIDTH)
	}

	//Connecting Clock
	io.TX_CLK <> io.CLK

	//TX data
	val tx_status = Reg(UInt(0, width=4))
	val tx_data_len = Reg(UInt(0, width=32))
	val tx_current_data = Reg(UInt(0, width=32))

	val buffer_current_data = Reg(UInt(0, width=32))

	when (io.RST) {
		tx_status := UInt(0, width=4)
	}

	//Transmit management section
	final val TX_WAITING  = UInt(0, width=4)
	final val TX_PREPARE  = UInt(1, width=4)
	final val TX_TRANSMIT = UInt(2, width=4)

	io.TX := Bool(false)
	io.TX_LAST := Bool(false)
	io.TX_LEN := UInt(0, width=32)
	io.TX_OFF := UInt(0, width=31)
	io.TX_DATA := UInt(0, width=C_PCI_DATA_WIDTH)
	io.TX_DATA_VALID := Bool(false)

	val buffer = Vec(
		for (i <- 0 until buffer_size) yield
		Reg(UInt(0, width=C_PCI_DATA_WIDTH))
	)

	when (io.data_valid) {
		tx_data_len := io.data_len * UInt(C_PCI_DATA_WIDTH/32, width=32)
		buffer(buffer_current_data) := io.data
		buffer_current_data := buffer_current_data + UInt(1, width=32)
	} .otherwise {
		buffer_current_data := UInt(0, width=32)
	}

	when (tx_status === TX_WAITING) {
		when (io.data_valid) {
			tx_status := TX_PREPARE

			tx_current_data := UInt(0, width=32)
		}
	} 

	when (tx_status === TX_PREPARE) {
		io.TX := Bool(true)
		io.TX_LEN := tx_data_len
		io.TX_DATA := buffer(UInt(0, width=32))
		//io.TX_DATA_VALID := Bool(true)

		when (io.TX_ACK) {
			io.TX_DATA_VALID := Bool(true)
			tx_status := TX_TRANSMIT
		}
	}

	when (tx_status === TX_TRANSMIT) {
		when (tx_current_data >= tx_data_len) {
			tx_status := TX_WAITING
		} .elsewhen (!io.TX_ACK && io.TX_DATA_REN) {
			io.TX := Bool(true)
			io.TX_LEN := tx_data_len
			io.TX_DATA_VALID := Bool(true)
			io.TX_DATA := buffer(tx_current_data / UInt(C_PCI_DATA_WIDTH/32, width=32))

			tx_current_data := tx_current_data + UInt(C_PCI_DATA_WIDTH/32, width=32)
		}
	}

	io.TX_LAST := io.TX
}
