[Ivy]
17FBB41A0EF7371A 9.3.1 #module
>Proto >Proto Collection #zClass
Hs0 HealthMonitorProcess Big #zClass
Hs0 RD #cInfo
Hs0 #process
Hs0 @AnnotationInP-0n ai ai #zField
Hs0 @TextInP .type .type #zField
Hs0 @TextInP .processKind .processKind #zField
Hs0 @TextInP .xml .xml #zField
Hs0 @TextInP .responsibility .responsibility #zField
Hs0 @UdInit f0 '' #zField
Hs0 @UdProcessEnd f1 '' #zField
Hs0 @PushWFArc f2 '' #zField
Hs0 @UdEvent f3 '' #zField
Hs0 @UdExitEnd f4 '' #zField
Hs0 @PushWFArc f5 '' #zField
>Proto Hs0 Hs0 HealthMonitorProcess #zField
Hs0 f0 guid 17FBB41A13277E0F #txt
Hs0 f0 method start(connector.github.demo.Data) #txt
Hs0 f0 inParameterDecl '<connector.github.demo.Data data> param;' #txt
Hs0 f0 inParameterMapAction 'out.data=param.data;
' #txt
Hs0 f0 outParameterDecl '<connector.github.demo.Data data> result;' #txt
Hs0 f0 outParameterMapAction 'result.data=in.data;
' #txt
Hs0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start(Data)</name>
    </language>
</elementInfo>
' #txt
Hs0 f0 83 51 26 26 -29 15 #rect
Hs0 f1 211 51 26 26 0 12 #rect
Hs0 f2 109 64 211 64 #arcP
Hs0 f3 guid 17FBB41A14EB8824 #txt
Hs0 f3 actionTable 'out=in;
' #txt
Hs0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>close</name>
    </language>
</elementInfo>
' #txt
Hs0 f3 83 147 26 26 -15 15 #rect
Hs0 f4 211 147 26 26 0 12 #rect
Hs0 f5 109 160 211 160 #arcP
>Proto Hs0 .type connector.github.demo.HealthMonitor.HealthMonitorData #txt
>Proto Hs0 .processKind HTML_DIALOG #txt
>Proto Hs0 -8 -8 16 16 16 26 #rect
Hs0 f0 mainOut f2 tail #connect
Hs0 f2 head f1 mainIn #connect
Hs0 f3 mainOut f5 tail #connect
Hs0 f5 head f4 mainIn #connect
