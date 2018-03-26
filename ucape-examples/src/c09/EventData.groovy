package c09
 
// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel
import groovyJCSP.*
import c9.MissedEventsCounter

class EventData implements Serializable, JCSPCopy {
	
  def int source = 0
  def int data = 0
  def int missed = -1 

  def copy() {
    def e = new EventData ( source: this.source, 
                            data: this.data, 
                            missed: this.missed )
    return e
  }  
  
  def String toString() {
      def s = "EventData -> [source: "
    s = s + source + ", data: "
    s = s + data + ", missed: " 
    s = s + missed + "]"

      //data received is equal to currently received data
     /* MissedEventsCounter.dataReceived = data
      def correctMissedValues = "\nData values are "
      if (MissedEventsCounter.countCorrectMissedDataValue() == missed || MissedEventsCounter.firstData == data)
          correctMissedValues += " correctly counted."
      else
          correctMissedValues += " wrong."
      //set current data number to dataSent as on ne
      MissedEventsCounter.dataSent = data*/
    return s //+ correctMissedValues
  }   
    
}


