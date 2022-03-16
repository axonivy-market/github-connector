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
do0 @PushWFArc f2 '' #zField
do0 @StartRequest f5 '' #zField
do0 @EndTask f6 '' #zField
do0 @RestClientCall f7 '' #zField
do0 @PushWFArc f8 '' #zField
do0 @PushWFArc f9 '' #zField
>Proto do0 do0 demo #zField
do0 f0 outLink start.ivp #txt
do0 f0 inParamDecl '<> param;' #txt
do0 f0 requestEnabled true #txt
do0 f0 triggerEnabled false #txt
do0 f0 callSignature start() #txt
do0 f0 caseData businessCase.attach=true #txt
do0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start.ivp</name>
    </language>
</elementInfo>
' #txt
do0 f0 @C|.responsibility Everybody #txt
do0 f0 81 49 30 30 -21 17 #rect
do0 f1 497 49 30 30 0 15 #rect
do0 f3 processCall github:call(connector.github.Data) #txt
do0 f3 requestActionDecl '<connector.github.Data data> param;' #txt
do0 f3 responseMappingAction 'out=in;
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
do0 f2 280 64 497 64 #arcP
do0 f5 outLink start2.ivp #txt
do0 f5 inParamDecl '<> param;' #txt
do0 f5 requestEnabled true #txt
do0 f5 triggerEnabled false #txt
do0 f5 callSignature start2() #txt
do0 f5 caseData businessCase.attach=true #txt
do0 f5 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start2.ivp</name>
    </language>
</elementInfo>
' #txt
do0 f5 @C|.responsibility Everybody #txt
do0 f5 81 145 30 30 -24 17 #rect
do0 f6 337 145 30 30 0 15 #rect
do0 f7 clientId 4895b78f-4d15-49f6-9754-de015d91d52e #txt
do0 f7 path /user/orgs #txt
do0 f7 queryParams 'per_page=10;
page=1;
' #txt
do0 f7 resultType java.util.List<com.github.api.client.OrganizationSimple> #txt
do0 f7 clientErrorCode ivy:error:rest:client #txt
do0 f7 statusErrorCode ivy:error:rest:client #txt
do0 f7 168 138 112 44 0 -8 #rect
do0 f8 111 160 168 160 #arcP
do0 f9 280 160 337 160 #arcP
>Proto do0 .type connector.github.demo.Data #txt
>Proto do0 .processKind NORMAL #txt
>Proto do0 0 0 32 24 18 0 #rect
>Proto do0 @|BIcon #fIcon
do0 f0 mainOut f4 tail #connect
do0 f4 head f3 mainIn #connect
do0 f3 mainOut f2 tail #connect
do0 f2 head f1 mainIn #connect
do0 f5 mainOut f8 tail #connect
do0 f8 head f7 mainIn #connect
do0 f7 mainOut f9 tail #connect
do0 f9 head f6 mainIn #connect
