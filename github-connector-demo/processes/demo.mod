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
do0 @RestClientCall f10 '' #zField
do0 @EndTask f11 '' #zField
do0 @StartRequest f12 '' #zField
do0 @PushWFArc f13 '' #zField
do0 @PushWFArc f14 '' #zField
do0 @StartRequest f15 '' #zField
do0 @EndTask f16 '' #zField
do0 @RestClientCall f17 '' #zField
do0 @PushWFArc f18 '' #zField
do0 @PushWFArc f19 '' #zField
do0 @StartRequest f20 '' #zField
do0 @UserDialog f21 '' #zField
do0 @EndTask f22 '' #zField
do0 @PushWFArc f23 '' #zField
do0 @PushWFArc f24 '' #zField
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
do0 f7 path /search/repositories #txt
do0 f7 queryParams 'per_page=10;
page=1;
q="user:jackra1n";
sort=;
order=;
' #txt
do0 f7 resultType com.github.api.client.InlineResponse20034 #txt
do0 f7 responseCode 'import com.github.api.client.RepoSearchResultItem;
import com.github.api.client.InlineResponse20034;
ivy.log.fatal(result.totalCount);
for (RepoSearchResultItem repo : result.items) {
	ivy.log.fatal(repo.getName());
}' #txt
do0 f7 clientErrorCode ivy:error:rest:client #txt
do0 f7 statusErrorCode ivy:error:rest:client #txt
do0 f7 168 138 112 44 0 -8 #rect
do0 f8 111 160 168 160 #arcP
do0 f9 280 160 337 160 #arcP
do0 f10 clientId 4895b78f-4d15-49f6-9754-de015d91d52e #txt
do0 f10 path /user/repos #txt
do0 f10 queryParams 'per_page=10;
page=1;
sort=;
type=;
direction=;
visibility=;
affiliation=;
since=;
before=;
' #txt
do0 f10 resultType java.util.List<com.github.api.client.Repository> #txt
do0 f10 responseCode 'import com.github.api.client.Repository;
import com.github.api.client.MinimalRepository;
import com.github.api.client.RepoSearchResultItem;
import com.github.api.client.InlineResponse20034;
ivy.log.fatal(response.readEntity(String.class));
ivy.log.fatal(result.size());
//for (RepoSearchResultItem repo : result.items) {
//for (MinimalRepository repo : result) {
for (Repository repo : result) {
	ivy.log.fatal(repo.size);
}' #txt
do0 f10 clientErrorCode ivy:error:rest:client #txt
do0 f10 statusErrorCode ivy:error:rest:client #txt
do0 f10 168 226 112 44 0 -8 #rect
do0 f11 337 233 30 30 0 15 #rect
do0 f12 outLink start3.ivp #txt
do0 f12 inParamDecl '<> param;' #txt
do0 f12 requestEnabled true #txt
do0 f12 triggerEnabled false #txt
do0 f12 callSignature start3() #txt
do0 f12 caseData businessCase.attach=true #txt
do0 f12 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start3.ivp</name>
    </language>
</elementInfo>
' #txt
do0 f12 @C|.responsibility Everybody #txt
do0 f12 81 233 30 30 -24 17 #rect
do0 f13 280 248 337 248 #arcP
do0 f14 111 248 168 248 #arcP
do0 f15 outLink start4.ivp #txt
do0 f15 inParamDecl '<> param;' #txt
do0 f15 requestEnabled true #txt
do0 f15 triggerEnabled false #txt
do0 f15 callSignature start4() #txt
do0 f15 caseData businessCase.attach=true #txt
do0 f15 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start4.ivp</name>
    </language>
</elementInfo>
' #txt
do0 f15 @C|.responsibility Everybody #txt
do0 f15 81 313 30 30 -24 17 #rect
do0 f16 337 313 30 30 0 15 #rect
do0 f17 clientId 4895b78f-4d15-49f6-9754-de015d91d52e #txt
do0 f17 path /user/repos #txt
do0 f17 queryParams 'per_page=10;
page=1;
sort=;
type=;
direction=;
visibility=;
affiliation=;
since=;
before=;
' #txt
do0 f17 resultType java.lang.String #txt
do0 f17 responseCode 'import List;
import com.github.api.client.Repository;
import com.github.api.client.MinimalRepository;
import com.github.api.client.RepoSearchResultItem;
import com.github.api.client.InlineResponse20034;
//ivy.log.fatal(response.readEntity(String.class));
Object links = response.getHeaders().get("Link");
ivy.log.fatal(links);
ivy.log.fatal(result);
//for (RepoSearchResultItem repo : result.items) {
//for (MinimalRepository repo : result) {' #txt
do0 f17 clientErrorCode ivy:error:rest:client #txt
do0 f17 statusErrorCode ivy:error:rest:client #txt
do0 f17 168 306 112 44 0 -8 #rect
do0 f18 111 328 168 328 #arcP
do0 f19 280 328 337 328 #arcP
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
do0 f20 81 401 30 30 -24 17 #rect
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
do0 f21 168 394 112 44 -35 -8 #rect
do0 f22 337 401 30 30 0 15 #rect
do0 f23 111 416 168 416 #arcP
do0 f24 280 416 337 416 #arcP
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
do0 f12 mainOut f14 tail #connect
do0 f14 head f10 mainIn #connect
do0 f10 mainOut f13 tail #connect
do0 f13 head f11 mainIn #connect
do0 f15 mainOut f18 tail #connect
do0 f18 head f17 mainIn #connect
do0 f17 mainOut f19 tail #connect
do0 f19 head f16 mainIn #connect
do0 f20 mainOut f23 tail #connect
do0 f23 head f21 mainIn #connect
do0 f21 mainOut f24 tail #connect
do0 f24 head f22 mainIn #connect
