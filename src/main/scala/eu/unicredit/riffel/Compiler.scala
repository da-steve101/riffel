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

import java.io.File
 
 object Compiler extends App {

 	val targetDir = new File("./verilog")

 	if (!targetDir.exists) targetDir.mkdir

 	val argz = Array("--backend", "v", "--compile", "--targetDir", targetDir.getAbsolutePath)

 	val bitWidth: Int = try Integer.parseInt(args(0)) catch {
 		case _ : Throwable => 64
 	}

 	val txBuffer: Int = try Integer.parseInt(args(1)) catch {
 		case _ : Throwable => 10
 	}

 	println(s"Compilig Riffel Bridge with $bitWidth bit and $txBuffer ts buffer size")

 	chiselMain(argz, () => Module(RiffelBridge(bitWidth, txBuffer)))
 }