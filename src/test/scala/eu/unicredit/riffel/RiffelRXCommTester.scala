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

case class RiffelRXCommTester(rc: RiffelRXComm) extends Tester(rc) {

	//STEP 1
	poke (rc.io.RX, 0)
	poke (rc.io.RX_LEN, 0)
	poke (rc.io.RX_DATA, 0)
	poke (rc.io.RX_DATA_VALID, 0)

	expect (rc.io.RX_ACK, 0)
	expect (rc.io.RX_DATA_REN, 0)
	expect (rc.io.data_valid, 0)
	expect (rc.io.data_len, 0)
	expect (rc.io.data, 0)

	step(1)

	//STEP 2
	poke (rc.io.RX, 1)
	poke (rc.io.RX_LEN, 4)
	poke (rc.io.RX_DATA, 0)
	poke (rc.io.RX_DATA_VALID, 0)

	expect (rc.io.RX_ACK, 0)
	expect (rc.io.RX_DATA_REN, 0)
	expect (rc.io.data_valid, 0)
	expect (rc.io.data_len, 0)
	expect (rc.io.data, 0)

	step(1)

	//STEP 3
	poke (rc.io.RX, 1)
	poke (rc.io.RX_LEN, 4)
	poke (rc.io.RX_DATA, 0)
	poke (rc.io.RX_DATA_VALID, 0)

	expect (rc.io.RX_ACK, 0)
	expect (rc.io.RX_DATA_REN, 0)
	expect (rc.io.data_valid, 0)
	expect (rc.io.data_len, 0)
	expect (rc.io.data, 0)

	step(1)

	//STEP 4
	poke (rc.io.RX, 1)
	poke (rc.io.RX_LEN, 4)
	poke (rc.io.RX_DATA, 0)
	poke (rc.io.RX_DATA_VALID, 1)

	expect (rc.io.RX_ACK, 1)
	expect (rc.io.RX_DATA_REN, 0)
	expect (rc.io.data_valid, 0)
	expect (rc.io.data_len, 0)
	expect (rc.io.data, 0)

	step(1)

	//STEP 5
	poke (rc.io.RX, 1)
	poke (rc.io.RX_LEN, 4)
	poke (rc.io.RX_DATA, 11)
	poke (rc.io.RX_DATA_VALID, 1)

	expect (rc.io.RX_ACK, 0)
	expect (rc.io.RX_DATA_REN, 1)
	expect (rc.io.data_valid, 1)
	expect (rc.io.data_len, 4)
	expect (rc.io.data, 11)

	step(1)

	//STEP 6
	poke (rc.io.RX, 0)
	poke (rc.io.RX_LEN, 0)
	poke (rc.io.RX_DATA, 22)
	poke (rc.io.RX_DATA_VALID, 1)

	expect (rc.io.RX_ACK, 0)
	expect (rc.io.RX_DATA_REN, 1)
	expect (rc.io.data_valid, 1)
	expect (rc.io.data_len, 4)
	expect (rc.io.data, 22)

	step(1)

	//STEP 7
	poke (rc.io.RX, 0)
	poke (rc.io.RX_LEN, 0)
	poke (rc.io.RX_DATA, 33)
	poke (rc.io.RX_DATA_VALID, 1)

	expect (rc.io.RX_ACK, 0)
	expect (rc.io.RX_DATA_REN, 1)
	expect (rc.io.data_valid, 1)
	expect (rc.io.data_len, 4)
	expect (rc.io.data, 33)

	step(1)

	//STEP 8
	poke (rc.io.RX, 0)
	poke (rc.io.RX_LEN, 0)
	poke (rc.io.RX_DATA, 44)
	poke (rc.io.RX_DATA_VALID, 1)

	expect (rc.io.RX_ACK, 0)
	expect (rc.io.RX_DATA_REN, 1)
	expect (rc.io.data_valid, 1)
	expect (rc.io.data_len, 4)
	expect (rc.io.data, 44)

	step(1)

	//STEP 9
	poke (rc.io.RX, 0)
	poke (rc.io.RX_LEN, 0)
	poke (rc.io.RX_DATA, 0)
	poke (rc.io.RX_DATA_VALID, 0)

	expect (rc.io.RX_ACK, 0)
	expect (rc.io.RX_DATA_REN, 0)
	expect (rc.io.data_valid, 0)
	expect (rc.io.data_len, 0)
	expect (rc.io.data, 0)

	step(1)

}
