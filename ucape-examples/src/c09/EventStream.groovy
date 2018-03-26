package c09

import c9.MissedEventsCounter

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel


import jcsp.lang.*
import groovyJCSP.*

class EventStream implements CSProcess {  
	
  def int source = 0
  def int initialValue = 0
  def int iterations = 10
  def ChannelOutput outChannel
    
  void run () {
    def i = initialValue
	//MissedEventsCounter.dataSent = i
    //MissedEventsCounter.firstData = i
    1.upto(iterations) {
      def e = new EventData ( source: source, data: i )
      outChannel.write(e)
      i = i + 1

    }
    println "Source $source has finished"
  }
}

