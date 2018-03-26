package c07

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import jcsp.lang.*
import groovyJCSP.*


class Client implements CSProcess{  
	
  def ChannelInput receiveChannel
  def ChannelOutput requestChannel
  def clientNumber   
  def selectList = [ ] 
   
  void run () {
    def iterations = selectList.size
   // println "Client $clientNumber has $iterations values in $selectList"

    def correctlyPassedValues = 0
    def incorrectValuesReceived =[]

   for ( i in 0 ..< iterations) {
       def key = selectList[i]
      requestChannel.write(key)
      def v = receiveChannel.read()
         if (v == key*10)
        {
            correctlyPassedValues++
        }
        else
        {
            incorrectValuesReceived << v
        }
    }

    println "Client $clientNumber has finished"
    if (correctlyPassedValues == iterations) {
      println "All values were sent and received correctly"
    }
    else
    {
      println "There were some incorrect values received $incorrectValuesReceived"
    }
  }
}
