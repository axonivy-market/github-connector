[Ivy]
17F92CE6CA3FD67B 9.3.1 #module
>Proto >Proto Collection #zClass
do0 demo Big #zClass
do0 B #cInfo
do0 #process
do0 @AnnotationInP-0n ai ai #zField
do0 @TextInP .type .type #zField
do0 @TextInP .processKind .processKind #zField
do0 @TextInP .xml .xml #zField
do0 @TextInP .responsibility .responsibility #zField
do0 @StartRequest f0 '' #zField
do0 @EndTask f1 '' #zField
do0 @CallSub f3 '' #zField
do0 @PushWFArc f4 '' #zField
do0 @StartRequest f20 '' #zField
do0 @UserDialog f21 '' #zField
do0 @EndTask f22 '' #zField
do0 @PushWFArc f23 '' #zField
do0 @PushWFArc f24 '' #zField
do0 @StartRequest f25 '' #zField
do0 @UserDialog f26 '' #zField
do0 @EndTask f27 '' #zField
do0 @PushWFArc f28 '' #zField
do0 @PushWFArc f29 '' #zField
do0 @UserDialog f2 '' #zField
do0 @PushWFArc f5 '' #zField
do0 @PushWFArc f6 '' #zField
>Proto do0 do0 demo #zField
do0 f0 outLink reposSimple.ivp #txt
do0 f0 inParamDecl '<> param;' #txt
do0 f0 requestEnabled true #txt
do0 f0 triggerEnabled false #txt
do0 f0 callSignature reposSimple() #txt
do0 f0 caseData businessCase.attach=true #txt
do0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>reposSimple.ivp</name>
    </language>
</elementInfo>
' #txt
do0 f0 @C|.responsibility Everybody #txt
do0 f0 81 49 30 30 -21 17 #rect
do0 f1 497 49 30 30 0 15 #rect
do0 f3 processCall github:call(connector.github.Data) #txt
do0 f3 requestActionDecl '<connector.github.Data data> param;' #txt
do0 f3 responseMappingAction 'out.repos=result.data.repos;
' #txt
do0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>github</name>
    </language>
</elementInfo>
' #txt
do0 f3 168 42 112 44 -17 -8 #rect
do0 f4 111 64 168 64 #arcP
do0 f20 outLink repoList.ivp #txt
do0 f20 inParamDecl '<> param;' #txt
do0 f20 requestEnabled true #txt
do0 f20 triggerEnabled false #txt
do0 f20 callSignature repoList() #txt
do0 f20 caseData businessCase.attach=true #txt
do0 f20 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>repoList.ivp</name>
    </language>
</elementInfo>
' #txt
do0 f20 @C|.responsibility Everybody #txt
do0 f20 81 145 30 30 -24 17 #rect
do0 f21 dialogId connector.github.demo.Repositories #txt
do0 f21 startMethod start(connector.github.demo.Data) #txt
do0 f21 requestActionDecl '<connector.github.demo.Data data> param;' #txt
do0 f21 responseMappingAction 'out=in;
' #txt
do0 f21 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>Repositories</name>
    </language>
</elementInfo>
' #txt
do0 f21 168 138 112 44 -35 -8 #rect
do0 f22 337 145 30 30 0 15 #rect
do0 f23 111 160 168 160 #arcP
do0 f24 280 160 337 160 #arcP
do0 f25 outLink healthMonitor.ivp #txt
do0 f25 inParamDecl '<> param;' #txt
do0 f25 requestEnabled true #txt
do0 f25 triggerEnabled false #txt
do0 f25 callSignature healthMonitor() #txt
do0 f25 caseData businessCase.attach=true #txt
do0 f25 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>healthMonitor.ivp</name>
    </language>
</elementInfo>
' #txt
do0 f25 @C|.responsibility Everybody #txt
do0 f25 81 241 30 30 -24 17 #rect
do0 f26 dialogId connector.github.demo.HealthMonitor #txt
do0 f26 startMethod start(connector.github.demo.Data) #txt
do0 f26 requestActionDecl '<connector.github.demo.Data data> param;' #txt
do0 f26 responseMappingAction 'out=in;
' #txt
do0 f26 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>HealthMonitor</name>
    </language>
</elementInfo>
' #txt
do0 f26 168 234 112 44 -38 -8 #rect
do0 f27 337 241 30 30 0 15 #rect
do0 f28 111 256 168 256 #arcP
do0 f29 280 256 337 256 #arcP
do0 f2 dialogId connector.github.demo.SimpleRepos #txt
do0 f2 startMethod start(java.util.List<com.github.api.client.Repository>) #txt
do0 f2 requestActionDecl '<java.util.List<com.github.api.client.Repository> repos> param;' #txt
do0 f2 requestMappingAction 'param.repos=in.repos;
' #txt
do0 f2 responseMappingAction 'out=in;
' #txt
do0 f2 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>SimpleRepos</name>
    </language>
</elementInfo>
' #txt
do0 f2 328 42 112 44 -38 -8 #rect
do0 f5 280 64 328 64 #arcP
do0 f6 440 64 497 64 #arcP
>Proto do0 .type connector.github.demo.Data #txt
>Proto do0 .processKind NORMAL #txt
>Proto do0 0 0 32 24 18 0 #rect
>Proto do0 @|BIcon #fIcon
do0 f0 mainOut f4 tail #connect
do0 f4 head f3 mainIn #connect
do0 f20 mainOut f23 tail #connect
do0 f23 head f21 mainIn #connect
do0 f21 mainOut f24 tail #connect
do0 f24 head f22 mainIn #connect
do0 f25 mainOut f28 tail #connect
do0 f28 head f26 mainIn #connect
do0 f26 mainOut f29 tail #connect
do0 f29 head f27 mainIn #connect
do0 f3 mainOut f5 tail #connect
do0 f5 head f2 mainIn #connect
do0 f2 mainOut f6 tail #connect
do0 f6 head f1 mainIn #connect
