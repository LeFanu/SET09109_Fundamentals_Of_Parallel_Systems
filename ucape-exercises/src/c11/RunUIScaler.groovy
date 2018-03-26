package c11


import jcsp.lang.*
import groovyJCSP.*
import groovyJCSP.plugAndPlay.*


def data = Channel.one2one()
def timedData = Channel.one2one()
def scaledData = Channel.one2one()
def oldScale = Channel.one2one()
def newScale = Channel.one2one()
def pause = Channel.one2one()

def network = [ new GNumbers ( outChannel: data.out() ),
                new GFixedDelay ( delay: 1000,
                        inChannel: data.in(),
                        outChannel: timedData.out() ),

                new ScaleForUI ( inChannel: timedData.in(),
                        outChannel: scaledData.out(),
                        factor: oldScale.out(),
                        suspend: pause.in(),
                        injector: newScale.in(),
                        multiplier: 2,
                        scalingFactor: 2
                        ),

                new ControllerConnectionForUI (
                        scalingFactorFromScaler: oldScale.in(),
                        suspendToScaler: pause.out(),
                        injectToScaler: newScale.out() ),

                new GPrint ( inChannel: scaledData.in(),
                        heading: "Original      Scaled",
                        delay: 0)
]

new PAR ( network ).run()
