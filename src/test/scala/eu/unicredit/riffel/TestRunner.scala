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

object TestRunner extends App {
	val argz = Array("--backend", "c", "--compile", "--genHarness", "--test", "--targetDir", "./target")

	chiselMainTest(argz, () => Module(RiffelRXComm(64))){
          rc => new RiffelRXCommTester(rc)}
	
	chiselMainTest(argz, () => Module(RiffelTXComm(64, 10))){
       	 rc => new RiffelTXCommTester(rc)}
}
