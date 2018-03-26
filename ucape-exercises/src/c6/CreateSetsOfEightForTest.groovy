package c6

import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput

class CreateSetsOfEightForTest implements CSProcess{
	
	def ChannelInput inChannel
	def finalList = []
	void run(){
		def outList = []
		def v = inChannel.read()
		while (v != -1){
			for ( i in 0 .. 7 ) {
				outList.add(v)
				v=inChannel.read()
			}
			finalList.add(outList)
			outList = []
		}
		println " Final list is ${finalList}"
	}
}