[Ivy]
17F9831500727FC1 9.3.1 #module
>Proto >Proto Collection #zClass
Rs0 RepositoriesProcess Big #zClass
Rs0 RD #cInfo
Rs0 #process
Rs0 @AnnotationInP-0n ai ai #zField
Rs0 @TextInP .type .type #zField
Rs0 @TextInP .processKind .processKind #zField
Rs0 @TextInP .xml .xml #zField
Rs0 @TextInP .responsibility .responsibility #zField
Rs0 @UdInit f0 '' #zField
Rs0 @UdProcessEnd f1 '' #zField
Rs0 @PushWFArc f2 '' #zField
Rs0 @UdEvent f3 '' #zField
Rs0 @UdExitEnd f4 '' #zField
Rs0 @PushWFArc f5 '' #zField
>Proto Rs0 Rs0 RepositoriesProcess #zField
Rs0 f0 guid 17F98315112D7189 #txt
Rs0 f0 method start(connector.github.demo.Data) #txt
Rs0 f0 inParameterDecl '<connector.github.demo.Data data> param;' #txt
Rs0 f0 inParameterMapAction 'out.data=param.data;
' #txt
Rs0 f0 outParameterDecl '<connector.github.demo.Data data> result;' #txt
Rs0 f0 outParameterMapAction 'result.data=in.data;
' #txt
Rs0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start(Data)</name>
    </language>
</elementInfo>
' #txt
Rs0 f0 83 51 26 26 -29 15 #rect
Rs0 f1 211 51 26 26 0 12 #rect
Rs0 f2 109 64 211 64 #arcP
Rs0 f3 guid 17F983151353B3C5 #txt
Rs0 f3 actionTable 'out=in;
' #txt
Rs0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>close</name>
    </language>
</elementInfo>
' #txt
Rs0 f3 83 147 26 26 -15 15 #rect
Rs0 f4 211 147 26 26 0 12 #rect
Rs0 f5 109 160 211 160 #arcP
>Proto Rs0 .type connector.github.demo.Repositories.RepositoriesData #txt
>Proto Rs0 .processKind HTML_DIALOG #txt
>Proto Rs0 -8 -8 16 16 16 26 #rect
Rs0 f0 mainOut f2 tail #connect
Rs0 f2 head f1 mainIn #connect
Rs0 f3 mainOut f5 tail #connect
Rs0 f5 head f4 mainIn #connect
