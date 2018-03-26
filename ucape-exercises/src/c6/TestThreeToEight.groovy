package c6

/**
 * Created by karol on 02/02/18.
 */
import jcsp.lang.*
import groovyJCSP.*

import c2.*

class TestThreeToEight extends GroovyTestCase{

    void testMessage()
    {
        def connect1 = Channel.one2one()
        def connect2 = Channel.one2one()

        def setsOfThree = new GenerateSetsOfThree ( outChannel: connect1.out())
        def streamList = new ListToStream ( inChannel: connect1.in(), outChannel: connect2.out() )
        def setsOfEight = new CreateSetsOfEightForTest ( inChannel: connect2.in() )

        def processList = [setsOfThree, streamList, setsOfEight]
        new PAR(processList).run()
        def expected = [[1,2,3,4,5,6,7,8],[9,10,11,12,13,14,15,16],[17,18,19,20,21,22,23,24]]
        def actual = setsOfEight.finalList
        assertTrue(expected == actual)
    }
}
