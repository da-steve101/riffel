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

case class RiffelBridge(C_PCI_DATA_WIDTH: Int, tx_buffer_size: Int) extends Module {
	val io = new Bundle {
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

        val TX_CLK = Bool(OUTPUT)
    	val TX = Bool(OUTPUT) 
    	val TX_ACK = Bool(INPUT)
    	val TX_LAST = Bool(OUTPUT)
    	val TX_LEN = UInt(OUTPUT, width=32)
    	val TX_OFF = UInt(OUTPUT, width=31)
    	val TX_DATA = UInt(OUTPUT, width=C_PCI_DATA_WIDTH)
    	val TX_DATA_VALID = Bool(OUTPUT)
    	val TX_DATA_REN = Bool(INPUT)

		val bridge_rx_data = UInt(OUTPUT, width=C_PCI_DATA_WIDTH)
		val bridge_rx_data_len = UInt(OUTPUT, width=32)
		val bridge_rx_data_en = Bool(OUTPUT)

		val bridge_tx_data = UInt(INPUT, width=C_PCI_DATA_WIDTH)
		val bridge_tx_data_len = UInt(INPUT, width=32)
		val bridge_tx_data_en = Bool(INPUT)	
	}

	val rx_comm = Module(RiffelRXComm(C_PCI_DATA_WIDTH))

	val tx_comm = Module(RiffelTXComm(C_PCI_DATA_WIDTH, tx_buffer_size))

	io.CLK 				<> rx_comm.io.CLK
	io.RST 				<> rx_comm.io.RST

	io.CLK 				<> tx_comm.io.CLK
	io.RST 				<> tx_comm.io.RST

	io.RX_CLK 			<> rx_comm.io.RX_CLK
	io.RX 				<> rx_comm.io.RX
	io.RX_ACK 			<> rx_comm.io.RX_ACK
	io.RX_LAST 			<> rx_comm.io.RX_LAST
	io.RX_LEN 			<> rx_comm.io.RX_LEN
	io.RX_OFF 			<> rx_comm.io.RX_OFF
	io.RX_DATA 			<> rx_comm.io.RX_DATA
	io.RX_DATA_VALID 	<> rx_comm.io.RX_DATA_VALID
	io.RX_DATA_REN 		<> rx_comm.io.RX_DATA_REN

	io.TX_CLK 			<> tx_comm.io.TX_CLK
	io.TX 				<> tx_comm.io.TX
	io.TX_ACK 			<> tx_comm.io.TX_ACK
	io.TX_LAST 			<> tx_comm.io.TX_LAST
	io.TX_LEN 			<> tx_comm.io.TX_LEN
	io.TX_OFF 			<> tx_comm.io.TX_OFF
	io.TX_DATA 			<> tx_comm.io.TX_DATA
	io.TX_DATA_VALID 	<> tx_comm.io.TX_DATA_VALID
	io.TX_DATA_REN 		<> tx_comm.io.TX_DATA_REN

	rx_comm.io.data 		<> io.bridge_rx_data
	rx_comm.io.data_len 	<> io.bridge_rx_data_len
	rx_comm.io.data_valid 	<> io.bridge_rx_data_en

	tx_comm.io.data 		<> io.bridge_tx_data
	tx_comm.io.data_len 	<> io.bridge_tx_data_len
	tx_comm.io.data_valid 	<> io.bridge_tx_data_en
}