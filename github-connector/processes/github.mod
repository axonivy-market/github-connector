[Ivy]
17F92A2C7E745913 9.3.1 #module
>Proto >Proto Collection #zClass
gb0 github Big #zClass
gb0 B #cInfo
gb0 #process
gb0 @AnnotationInP-0n ai ai #zField
gb0 @TextInP .type .type #zField
gb0 @TextInP .processKind .processKind #zField
gb0 @TextInP .xml .xml #zField
gb0 @TextInP .responsibility .responsibility #zField
gb0 @StartSub f0 '' #zField
gb0 @EndSub f1 '' #zField
gb0 @RestClientCall f3 '' #zField
gb0 @PushWFArc f2 '' #zField
gb0 @PushWFArc f4 '' #zField
>Proto gb0 gb0 github #zField
gb0 f0 inParamDecl '<connector.github.Data data> param;' #txt
gb0 f0 inParamTable 'out.data=param.data;
' #txt
gb0 f0 outParamDecl '<connector.github.Data data> result;' #txt
gb0 f0 outParamTable 'result.data=in.data;
' #txt
gb0 f0 callSignature call(connector.github.Data) #txt
gb0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>call(Data)</name>
    </language>
</elementInfo>
' #txt
gb0 f0 81 49 30 30 -26 17 #rect
gb0 f0 res:/webContent/icons/GitHub-Mark-64px.png?small #fDecoratorIcon
gb0 f1 337 49 30 30 0 15 #rect
gb0 f3 clientId 4895b78f-4d15-49f6-9754-de015d91d52e #txt
gb0 f3 path /user/repos #txt
gb0 f3 queryParams 'visibility=;
affiliation=;
type=;
sort=;
direction=;
per_page=50;
page=1;
since=;
before=;
' #txt
gb0 f3 resultType java.util.List<com.github.api.client.Repository> #txt
gb0 f3 responseMapping 'out.data.Repos=result;
' #txt
gb0 f3 clientErrorCode ivy:error:rest:client #txt
gb0 f3 statusErrorCode ivy:error:rest:client #txt
gb0 f3 168 42 112 44 0 -8 #rect
gb0 f2 111 64 168 64 #arcP
gb0 f4 280 64 337 64 #arcP
>Proto gb0 .type connector.github.githubData #txt
>Proto gb0 .processKind CALLABLE_SUB #txt
>Proto gb0 0 0 32 24 18 0 #rect
>Proto gb0 @|BIcon #fIcon
gb0 f2 head f3 mainIn #connect
gb0 f4 head f1 mainIn #connect
gb0 f0 mainOut f2 tail #connect
gb0 f3 mainOut f4 tail #connect
