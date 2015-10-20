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

        setModuleName("RIFFA2_BRIDGE")

        RX_CLK.setName("CHNL_RX_CLK")
        RX.setName("CHNL_RX")
        RX_ACK.setName("CHNL_RX_ACK")
        RX_LAST.setName("CHNL_RX_LAST")
        RX_LEN.setName("CHNL_RX_LEN")
        RX_OFF.setName("CHNL_RX_OFF")
        RX_DATA.setName("CHNL_RX_DATA")
        RX_DATA_VALID.setName("CHNL_RX_DATA_VALID")
        RX_DATA_REN.setName("CHNL_RX_DATA_REN")

        TX_CLK.setName("CHNL_TX_CLK")
        TX_ACK.setName("CHNL_TX_ACK")
        TX_LAST.setName("CHNL_TX_LAST")
        TX_LEN.setName("CHNL_TX_LEN")
        TX_OFF.setName("CHNL_TX_OFF")
        TX_DATA.setName("CHNL_TX_DATA")
        TX_DATA_VALID.setName("CHNL_TX_DATA_VALID")
        TX_DATA_REN.setName("CHNL_TX_DATA_REN")

	val rx_comm = Module(RiffelRXComm(C_PCI_DATA_WIDTH))

	val tx_comm = Module(RiffelTXComm(C_PCI_DATA_WIDTH, tx_buffer_size))

	rx_comm.io.CLK := clock
	rx_comm.io.RST := reset

	tx_comm.io.CLK := clock
	tx_comm.io.RST := reset

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
