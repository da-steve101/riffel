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

case class RiffelTXCommTester(rc: RiffelTXComm) extends Tester(rc) {
	//STEP 1
	poke (rc.io.data_valid, 0)
	poke (rc.io.data_len, 0)
	poke (rc.io.data, 0)

	poke (rc.io.TX_ACK, 0)
	poke (rc.io.TX_DATA_REN, 0)

	expect (rc.io.TX, 0)
	expect (rc.io.TX_LEN, 0)
	expect (rc.io.TX_DATA, 0)
	expect (rc.io.TX_DATA_VALID, 0)
	
	step(1)

	//STEP 2
	poke (rc.io.data_valid, 1)
	poke (rc.io.data_len, 3)
	poke (rc.io.data, 11)

	poke (rc.io.TX_ACK, 0)
	poke (rc.io.TX_DATA_REN, 0)

	expect (rc.io.TX, 0)
	expect (rc.io.TX_LEN, 0)
	expect (rc.io.TX_DATA, 0)
	expect (rc.io.TX_DATA_VALID, 0)

	step(1)

	//STEP 3
	poke (rc.io.data_valid, 1)
	poke (rc.io.data_len, 3)
	poke (rc.io.data, 22)

	poke (rc.io.TX_ACK, 0)
	poke (rc.io.TX_DATA_REN, 0)

	expect (rc.io.TX, 1)
	expect (rc.io.TX_LEN, 6)
	expect (rc.io.TX_DATA, 11)
	expect (rc.io.TX_DATA_VALID, 0)

	step(1)

	//STEP 4
	poke (rc.io.data_valid, 1)
	poke (rc.io.data_len, 3)
	poke (rc.io.data, 33)

	poke (rc.io.TX_ACK, 1)
	poke (rc.io.TX_DATA_REN, 0)

	expect (rc.io.TX, 1)
	expect (rc.io.TX_LEN, 6)
	expect (rc.io.TX_DATA, 11)
	expect (rc.io.TX_DATA_VALID, 1)

	step(1)

	//STEP 5
	poke (rc.io.data_valid, 0)
	poke (rc.io.data_len, 0)
	poke (rc.io.data, 0)

	poke (rc.io.TX_ACK, 0)
	poke (rc.io.TX_DATA_REN, 1)

	expect (rc.io.TX, 1)
	expect (rc.io.TX_LEN, 6)
	expect (rc.io.TX_DATA, 11)
	expect (rc.io.TX_DATA_VALID, 1)

	step(1)	

	//STEP 6
	poke (rc.io.data_valid, 0)
	poke (rc.io.data_len, 0)
	poke (rc.io.data, 0)

	poke (rc.io.TX_ACK, 0)
	poke (rc.io.TX_DATA_REN, 1)

	expect (rc.io.TX, 1)
	expect (rc.io.TX_LEN, 6)
	expect (rc.io.TX_DATA, 22)
	expect (rc.io.TX_DATA_VALID, 1)
	
	step(1)

	//STEP 7
	poke (rc.io.data_valid, 0)
	poke (rc.io.data_len, 0)
	poke (rc.io.data, 0)

	poke (rc.io.TX_ACK, 0)
	poke (rc.io.TX_DATA_REN, 1)

	expect (rc.io.TX, 1)
	expect (rc.io.TX_LEN, 6)
	expect (rc.io.TX_DATA, 33)
	expect (rc.io.TX_DATA_VALID, 1)
	
	step(1)	

	//STEP 8
	poke (rc.io.data_valid, 0)
	poke (rc.io.data_len, 0)
	poke (rc.io.data, 0)

	poke (rc.io.TX_ACK, 0)
	poke (rc.io.TX_DATA_REN, 0)

	expect (rc.io.TX, 0)
	expect (rc.io.TX_LEN, 0)
	expect (rc.io.TX_DATA, 0)
	expect (rc.io.TX_DATA_VALID, 0)
	
	step(1)
}
