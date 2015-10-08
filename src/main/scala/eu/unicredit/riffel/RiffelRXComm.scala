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

case class RiffelRXComm(C_PCI_DATA_WIDTH: Int) extends Module {
	val io = new Bundle  {
    	val CLK = Bool(INPUT)
    	val RST = Bool(INPUT)
    
    	val RX_CLK = Bool(OUTPUT) 
    	val RX = Bool(INPUT)
    	val RX_ACK = Bool(OUTPUT)
    	val RX_LAST = Bool(INPUT)
    	val RX_LEN = UInt(INPUT, width=32)
    	val RX_OFF = UInt(INPUT, width=31)
    	val RX_DATA = UInt(INPUT, width=C_PCI_DATA_WIDTH)
    	val RX_DATA_VALID = Bool(INPUT)
    	val RX_DATA_REN = Bool(OUTPUT)

    	val data_valid = Bool(OUTPUT)
    	val data_len = UInt(OUTPUT, width=32)
    	val data = UInt(OUTPUT, width=C_PCI_DATA_WIDTH)
    }

	//Connecting Clock
	io.RX_CLK <> io.CLK

	//RX data
	val rx_status = Reg(UInt(0, width=4))
	val rx_data_len = Reg(UInt(0, width=32))
	val rx_current_data = Reg(UInt(0, width=32))

	when (io.RST) {
		rx_status := UInt(0, width=4)
	}

	//Receive management section
	final val RX_WAITING = UInt(0, width=4)
	final val RX_PREPARE = UInt(1, width=4)
	final val RX_RECEIVE = UInt(2, width=4)

	io.RX_ACK := Bool(false)
	io.RX_DATA_REN := Bool(false)

	io.data_valid := Bool(false)
	io.data_len := UInt(0, width=32)
	io.data := UInt(0, width=C_PCI_DATA_WIDTH)

	when (rx_status === RX_WAITING) {
		rx_current_data := UInt(0, width=32)
		rx_data_len := UInt(0, width=32)

		when (io.RX) {
			rx_current_data := UInt(0, width=32)

			rx_data_len := io.RX_LEN

			rx_status := RX_PREPARE
		}
	} 

	when (rx_status === RX_PREPARE) {

		io.RX_ACK := io.RX_DATA_VALID

		when (io.RX_DATA_VALID) {
			rx_data_len := io.RX_LEN

			rx_status := RX_RECEIVE
		}
	} 

	when (rx_status === RX_RECEIVE) {
		io.RX_DATA_REN := io.RX_DATA_VALID

		io.data_valid := io.RX_DATA_VALID

		io.data_len := rx_data_len

		io.data := io.RX_DATA

		rx_current_data := rx_current_data + UInt(C_PCI_DATA_WIDTH/32, width=32)

		when (rx_current_data > rx_data_len || rx_data_len === UInt(1, width=32)) {
			rx_status := RX_WAITING	
		}
	}
}
