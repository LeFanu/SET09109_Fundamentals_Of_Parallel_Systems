package c9

/**
 * Created by karol on 10/02/18.
 */
class MissedEventsCounter {

    def static int dataSent
    def static int dataReceived
    def static int firstData;


    def static countCorrectMissedDataValue(){
        return (dataReceived - (dataSent + 1))
    }
}
