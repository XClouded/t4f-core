/* 3.0.14-67775 */ 
(function(){var requirejs,require,define;(function(e){function a(e,t){var n=t&&t.split("/"),i=r.map,s=i&&i["*"]||{},o,u,a,f,l,c,h;if(e&&e.charAt(0)==="."&&t){n=n.slice(0,n.length-1),e=n.concat(e.split("/"));for(l=0;h=e[l];l++){if(h==="."){e.splice(l,1),l-=1;}else{if(h===".."){if(l===1&&(e[2]===".."||e[0]==="..")){return !0;}l>0&&(e.splice(l-1,2),l-=2);}}}e=e.join("/");}if((n||s)&&i){o=e.split("/");for(l=o.length;l>0;l-=1){u=o.slice(0,l).join("/");if(n){for(c=n.length;c>0;c-=1){a=i[n.slice(0,c).join("/")];if(a){a=a[u];if(a){f=a;break;}}}}f=f||s[u];if(f){o.splice(0,l,f),e=o.join("/");break;}}}return e;}function f(t,n){return function(){return u.apply(e,s.call(arguments,0).concat([t,n]));};}function l(e){return function(t){return a(t,e);};}function c(e){return function(n){t[e]=n;};}function h(r){if(n.hasOwnProperty(r)){var s=n[r];delete n[r],i[r]=!0,o.apply(e,s);}if(!t.hasOwnProperty(r)){throw new Error("No "+r);}return t[r];}function p(e,t){var n,r,i=e.indexOf("!");return i!==-1?(n=a(e.slice(0,i),t),e=e.slice(i+1),r=h(n),r&&r.normalize?e=r.normalize(e,l(t)):e=a(e,t)):e=a(e,t),{f:n?n+"!"+e:e,n:e,p:r};}function d(e){return function(){return r&&r.config&&r.config[e]||{};};}var t={},n={},r={},i={},s=[].slice,o,u;o=function(r,s,o,u){var a=[],l,v,m,g,y,b;u=u||r;if(typeof o=="function"){s=!s.length&&o.length?["require","exports","module"]:s;for(b=0;b<s.length;b++){y=p(s[b],u),m=y.f;if(m==="require"){a[b]=f(r);}else{if(m==="exports"){a[b]=t[r]={},l=!0;}else{if(m==="module"){v=a[b]={id:r,uri:"",exports:t[r],config:d(r)};}else{if(t.hasOwnProperty(m)||n.hasOwnProperty(m)){a[b]=h(m);}else{if(y.p){y.p.load(y.n,f(u,!0),c(m),{}),a[b]=t[m];}else{if(!i[m]){throw new Error(r+" missing "+m);}}}}}}}g=o.apply(t[r],a);if(r){if(v&&v.exports!==e&&v.exports!==t[r]){t[r]=v.exports;}else{if(g!==e||!l){t[r]=g;}}}}else{r&&(t[r]=o);}},requirejs=require=u=function(t,n,i,s){return typeof t=="string"?h(p(t,n).f):(t.splice||(r=t,n.splice?(t=n,n=i,i=null):t=e),n=n||function(){},s?o(e,t,n,i):setTimeout(function(){o(e,t,n,i);},15),u);},u.config=function(e){return r=e,u;},define=function(e,t,r){t.splice||(r=t,t=[]),n[e]=[e,t,r];},define.amd={jQuery:!0};})(),define("../vendor/almond",function(){}),fortyone=new function(){this.e=(new Date(2005,0,15)).getTimezoneOffset(),this.f=(new Date(2005,6,15)).getTimezoneOffset(),this.plugins=[],this.d={Flash:["ShockwaveFlash.ShockwaveFlash",function(e){return e.getVariable("$version");}],Director:["SWCtl.SWCtl",function(e){return e.ShockwaveVersion("");}]},this.r=function(e){var t;try{t=document.getElementById(e);}catch(n){}if(t===null||typeof t=="undefined"){try{t=document.getElementsByName(e)[0];}catch(r){}}if(t===null||typeof t=="undefined"){for(var i=0;i<document.forms.length;i++){for(var s=document.forms[i],o=0;o<s.elements.length;o++){var u=s[o];if(u.name===e||u.id===e){return u;}}}}return t;},this.b=function(e){var t="";try{typeof this.c.getComponentVersion!="undefined"&&(t=this.c.getComponentVersion(e,"ComponentID"));}catch(n){e=n.message.length,e=e>40?40:e,t=escape(n.message.substr(0,e));}return t;},this.exec=function(b){for(var c=0;c<b.length;c++){try{var d=eval(b[c]);if(d){return d;}}catch(e){}}return"";},this.p=function(e){var t="";try{if(navigator.plugins&&navigator.plugins.length){var n=RegExp(e+".* ([0-9._]+)");for(e=0;e<navigator.plugins.length;e++){var r=n.exec(navigator.plugins[e].name);r===null&&(r=n.exec(navigator.plugins[e].description)),r&&(t=r[1]);}}else{if(window.ActiveXObject&&this.d[e]){try{var i=new ActiveXObject(this.d[e][0]);t=this.d[e][1](i);}catch(s){t="";}}}}catch(o){t=o.message;}return t;},this.q=function(){for(var e=["Acrobat","Flash","QuickTime","Java Plug-in","Director","Office"],t=0;t<e.length;t++){var n=e[t];this.plugins[n]=this.p(n);}},this.g=function(){return Math.abs(this.e-this.f);},this.h=function(){return this.g()!==0;},this.i=function(e){var t=Math.min(this.e,this.f);return this.h()&&e.getTimezoneOffset()===t;},this.n=function(e){var t=0;return t=0,this.i(e)&&(t=this.g()),t=-(e.getTimezoneOffset()+t)/60;},this.j=function(e,t,n,r){typeof r!="boolean"&&(r=!1);for(var i=!0,s;(s=e.indexOf(t))>=0&&(r||i);){e=e.substr(0,s)+n+e.substr(s+t.length),i=!1;}return e;},this.m=function(){return(new Date(2005,5,7,21,33,44,888)).toLocaleString();},this.k=function(b){var c=new Date,d=[function(){return"TF1";},function(){return"015";},function(){return ScriptEngineMajorVersion();},function(){return ScriptEngineMinorVersion();},function(){return ScriptEngineBuildVersion();},function(e){return e.b("{7790769C-0471-11D2-AF11-00C04FA35D02}");},function(e){return e.b("{89820200-ECBD-11CF-8B85-00AA005B4340}");},function(e){return e.b("{283807B5-2C60-11D0-A31D-00AA00B92C03}");},function(e){return e.b("{4F216970-C90C-11D1-B5C7-0000F8051515}");},function(e){return e.b("{44BBA848-CC51-11CF-AAFA-00AA00B6015C}");},function(e){return e.b("{9381D8F2-0288-11D0-9501-00AA00B911A5}");},function(e){return e.b("{4F216970-C90C-11D1-B5C7-0000F8051515}");},function(e){return e.b("{5A8D6EE0-3E18-11D0-821E-444553540000}");},function(e){return e.b("{89820200-ECBD-11CF-8B85-00AA005B4383}");},function(e){return e.b("{08B0E5C0-4FCB-11CF-AAA5-00401C608555}");},function(e){return e.b("{45EA75A0-A269-11D1-B5BF-0000F8051515}");},function(e){return e.b("{DE5AED00-A4BF-11D1-9948-00C04F98BBC9}");},function(e){return e.b("{22D6F312-B0F6-11D0-94AB-0080C74C7E95}");},function(e){return e.b("{44BBA842-CC51-11CF-AAFA-00AA00B6015B}");},function(e){return e.b("{3AF36230-A269-11D1-B5BF-0000F8051515}");},function(e){return e.b("{44BBA840-CC51-11CF-AAFA-00AA00B6015C}");},function(e){return e.b("{CC2A9BA0-3BDD-11D0-821E-444553540000}");},function(e){return e.b("{08B0E5C0-4FCB-11CF-AAA5-00401C608500}");},function(){return eval("navigator.appCodeName");},function(){return eval("navigator.appName");},function(){return eval("navigator.appVersion");},function(e){return e.exec(["navigator.productSub","navigator.appMinorVersion"]);},function(){return eval("navigator.browserLanguage");},function(){return eval("navigator.cookieEnabled");},function(e){return e.exec(["navigator.oscpu","navigator.cpuClass"]);},function(){return eval("navigator.onLine");},function(){return eval("navigator.platform");},function(){return eval("navigator.systemLanguage");},function(){return eval("navigator.userAgent");},function(e){return e.exec(["navigator.language","navigator.userLanguage"]);},function(){return eval("document.defaultCharset");},function(){return eval("document.domain");},function(){return eval("screen.deviceXDPI");},function(){return eval("screen.deviceYDPI");},function(){return eval("screen.fontSmoothingEnabled");},function(){return eval("screen.updateInterval");},function(e){return e.h();},function(e){return e.i(c);},function(){return"@UTC@";},function(e){return e.n(c);},function(e){return e.m();},function(){return eval("screen.width");},function(){return eval("screen.height");},function(e){return e.plugins.Acrobat;},function(e){return e.plugins.Flash;},function(e){return e.plugins.QuickTime;},function(e){return e.plugins["Java Plug-in"];},function(e){return e.plugins.Director;},function(e){return e.plugins.Office;},function(){return(new Date).getTime()-c.getTime();},function(e){return e.e;},function(e){return e.f;},function(){return c.toLocaleString();},function(){return eval("screen.colorDepth");},function(){return eval("window.screen.availWidth");},function(){return eval("window.screen.availHeight");},function(){return eval("window.screen.availLeft");},function(){return eval("window.screen.availTop");},function(e){return e.a("Acrobat");},function(e){return e.a("Adobe SVG");},function(e){return e.a("Authorware");},function(e){return e.a("Citrix ICA");},function(e){return e.a("Director");},function(e){return e.a("Flash");},function(e){return e.a("MapGuide");},function(e){return e.a("MetaStream");},function(e){return e.a("PDFViewer");},function(e){return e.a("QuickTime");},function(e){return e.a("RealOne");},function(e){return e.a("RealPlayer Enterprise");},function(e){return e.a("RealPlayer Plugin");},function(e){return e.a("Seagate Software Report");},function(e){return e.a("Silverlight");},function(e){return e.a("Windows Media");},function(e){return e.a("iPIX");},function(e){return e.a("nppdf.so");},function(e){return e.o();}];this.q();for(var e="",f=0;f<d.length;f++){b&&(e+=this.j(d[f].toString(),'"',"'",!0),e+="=");var g;try{g=d[f](this);}catch(h){g="";}e+=b?g:escape(g),e+=";",b&&(e+="\\n");}return e=this.j(e,escape("@UTC@"),(new Date).getTime());},this.l=function(e){try{if(!e){return this.k();}var t;t=this.r(e);if(t!==null){try{t.value=this.k();}catch(n){t.value=escape(n.message);}}}catch(r){}},this.a=function(e){try{if(navigator.plugins&&navigator.plugins.length){for(var t=0;t<navigator.plugins.length;t++){var n=navigator.plugins[t];if(n.name.indexOf(e)>=0){return n.name+(n.description?"|"+n.description:"");}}}}catch(r){}return"";},this.o=function(){var e=document.createElement("span");e.innerHTML="&nbsp;",e.style.position="absolute",e.style.left="-9999px",document.body.appendChild(e);var t=e.offsetHeight;return document.body.removeChild(e),t;};};try{fortyone.c=document.createElement("span"),typeof fortyone.c.addBehavior!="undefined"&&fortyone.c.addBehavior("#default#clientCaps");}catch(i){}window.fortyone=fortyone,window.fortyone.collect=fortyone.l,define("../vendor/fortyone",function(){}),typeof JSON!="object"&&(JSON={}),function(){function f(e){return e<10?"0"+e:e;}function quote(e){return escapable.lastIndex=0,escapable.test(e)?'"'+e.replace(escapable,function(e){var t=meta[e];return typeof t=="string"?t:"\\u"+("0000"+e.charCodeAt(0).toString(16)).slice(-4);})+'"':'"'+e+'"';}function str(e,t){var n,r,i,s,o=gap,u,a=t[e];a&&typeof a=="object"&&typeof a.toJSON=="function"&&(a=a.toJSON(e)),typeof rep=="function"&&(a=rep.call(t,e,a));switch(typeof a){case"string":return quote(a);case"number":return isFinite(a)?String(a):"null";case"boolean":case"null":return String(a);case"object":if(!a){return"null";}gap+=indent,u=[];if(Object.prototype.toString.apply(a)==="[object Array]"){s=a.length;for(n=0;n<s;n+=1){u[n]=str(n,a)||"null";}return i=u.length===0?"[]":gap?"[\n"+gap+u.join(",\n"+gap)+"\n"+o+"]":"["+u.join(",")+"]",gap=o,i;}if(rep&&typeof rep=="object"){s=rep.length;for(n=0;n<s;n+=1){typeof rep[n]=="string"&&(r=rep[n],i=str(r,a),i&&u.push(quote(r)+(gap?": ":":")+i));}}else{for(r in a){Object.prototype.hasOwnProperty.call(a,r)&&(i=str(r,a),i&&u.push(quote(r)+(gap?": ":":")+i));}}return i=u.length===0?"{}":gap?"{\n"+gap+u.join(",\n"+gap)+"\n"+o+"}":"{"+u.join(",")+"}",gap=o,i;}}typeof Date.prototype.toJSON!="function"&&(Date.prototype.toJSON=function(e){return isFinite(this.valueOf())?this.getUTCFullYear()+"-"+f(this.getUTCMonth()+1)+"-"+f(this.getUTCDate())+"T"+f(this.getUTCHours())+":"+f(this.getUTCMinutes())+":"+f(this.getUTCSeconds())+"Z":null;},String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function(e){return this.valueOf();});var cx=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,escapable=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,gap,indent,meta={"\b":"\\b","	":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"},rep;typeof JSON.stringify!="function"&&(JSON.stringify=function(e,t,n){var r;gap="",indent="";if(typeof n=="number"){for(r=0;r<n;r+=1){indent+=" ";}}else{typeof n=="string"&&(indent=n);}rep=t;if(!t||typeof t=="function"||typeof t=="object"&&typeof t.length=="number"){return str("",{"":e});}throw new Error("JSON.stringify");}),typeof JSON.parse!="function"&&(JSON.parse=function(text,reviver){function walk(e,t){var n,r,i=e[t];if(i&&typeof i=="object"){for(n in i){Object.prototype.hasOwnProperty.call(i,n)&&(r=walk(i,n),r!==undefined?i[n]=r:delete i[n]);}}return reviver.call(e,t,i);}var j;text=String(text),cx.lastIndex=0,cx.test(text)&&(text=text.replace(cx,function(e){return"\\u"+("0000"+e.charCodeAt(0).toString(16)).slice(-4);}));if(/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,"@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,"]").replace(/(?:^|:|,)(?:\s*\[)+/g,""))){return j=eval("("+text+")"),typeof reviver=="function"?walk({"":j},""):j;}throw new SyntaxError("JSON.parse");});}(),define("../vendor/json2",function(){}),define("../src/bootstrap",[],function(){var e=function(e,t){var n=t.split("."),r=e,i;i=n.length;for(var s=0;s<i;s++){typeof r[n[s]]=="undefined"&&(r[n[s]]={}),r=r[n[s]];}return r;};typeof BKTAG=="undefined"&&e(window,"BKTAG"),BKTAG.ns=e;var t={createFrame:function(e){var t=document.createElement("iframe");return t.setAttribute("name",e),t.setAttribute("id",e),t.setAttribute("title","bk"),t.style.border="0px",t.style.width="0px",t.style.height="0px",typeof bk_frameLoad=="function"&&(t.onload=bk_frameLoad),t.src="javascript:void(0)",t;},checkFrame:function(e){var n="__bkframe";if(typeof frames[n]=="undefined"||typeof document.getElementById(n)=="undefined"){var r=t.createFrame(n),i=document.getElementsByTagName("body")[0];i&&i.appendChild(r);}typeof e=="function"&&e();}};return t;}),define("../vendor/htmlparser",[],function(){var e=function(e){var t={},n=e.split(",");for(var r=0;r<n.length;r++){t[n[r]]=!0;}return t;},t={leftTrim:function(e){return e.replace(/^\s+/,"");},startTag:/^<(\w+)((?:\s+\w+(?:\s*=\s*(?:(?:"[^"]*")|(?:'[^']*')|[^>\s]+))?)*)\s*(\/?)>/,endTag:/^<\/(\w+)[^>]*>/,attr:/(\w+)(?:\s*=\s*(?:(?:"((?:\\.|[^"])*)")|(?:'((?:\\.|[^'])*)')|([^>\s]+)))?/g,empty:e("area,base,basefont,br,col,frame,hr,img,input,isindex,link,meta,param,embed"),block:e("address,applet,blockquote,button,center,dd,del,dir,div,dl,dt,fieldset,form,frameset,hr,iframe,ins,isindex,li,map,menu,noframes,noscript,NOSCRIPT,object,ol,p,pre,script,SCRIPT,table,tbody,td,tfoot,th,thead,tr,ul"),inline:e("a,abbr,acronym,applet,b,basefont,bdo,big,br,button,cite,code,del,dfn,em,font,i,iframe,img,input,ins,kbd,label,map,object,q,s,samp,script,SCRIPT,select,small,span,strike,strong,sub,sup,textarea,tt,u,var"),closeSelf:e("colgroup,dd,dt,li,options,p,td,tfoot,th,thead,tr"),fillAttrs:e("checked,compact,declare,defer,disabled,ismap,multiple,nohref,noresize,noshade,nowrap,readonly,selected"),special:e("script,SCRIPT,style"),one:e("html,head,body,title"),structure:{link:"head",base:"head"},htmlParser:function(e,n){function c(e,r,i,s){if(t.block[r]){while(o.last()&&t.inline[o.last()]){h("",o.last());}}t.closeSelf[r]&&o.last()==r&&h("",r),s=t.empty[r]||!!s,s||o.push(r);if(n.start){var u=[];i.replace(t.attr,function(e,n){var r=arguments[2]?arguments[2]:arguments[3]?arguments[3]:arguments[4]?arguments[4]:t.fillAttrs[n]?n:"";u.push({name:n,value:r,escaped:r.replace(/(^|[^\\])"/g,'$1\\"')});}),n.start&&n.start(r,u,s);}}function h(e,t){if(!t){var r=0;}else{for(var r=o.length-1;r>=0;r--){if(o[r]==t){break;}}}if(r>=0){for(var i=o.length-1;i>=r;i--){n.end&&n.end(o[i]);}o.length=r;}}var r,i,s,o=[],u=e;o.last=function(){return this[this.length-1];};while(e){i=!0,e=t.leftTrim(e);if(!o.last()||!t.special[o.last()]){e.indexOf("<!--")==0?(r=e.indexOf("-->"),r>=0&&(n.comment&&n.comment(e.substring(4,r)),e=e.substring(r+3),i=!1)):e.indexOf("</")==0?(s=e.match(t.endTag),s&&(e=e.substring(s[0].length),s[0].replace(t.endTag,h),i=!1)):e.indexOf("<")==0&&(s=e.match(t.startTag),s&&(e=e.substring(s[0].length),s[0].replace(t.startTag,c),i=!1));if(i){r=e.indexOf("<");var a=r<0?e:e.substring(0,r);e=r<0?"":e.substring(r),n.chars&&n.chars(a);}}else{var f=new RegExp("</"+o.last()+">","i"),r=e.search(f),l=e.substring(0,r);l.length>0&&(n.chars&&n.chars(l),e=e.replace(l,"")),e=e.replace(f,""),h("",o.last());}if(e==u){throw"Parse Error: "+e;}u=e;}h();},htmlToDom:function(e,n){var r=[],i=n.documentElement||n.getOwnerDocument&&n.getOwnerDocument()||n;!i&&n.createElement&&function(){var e=n.createElement("html"),t=n.createElement("head");t.appendChild(n.createElement("title")),e.appendChild(t),e.appendChild(n.createElement("body")),n.appendChild(e);}();if(n.getElementsByTagName){for(var s in t.one){t.one[s]=n.getElementsByTagName(s)[0];}}var o=t.one.body;t.htmlParser(e,{start:function(e,i,s){if(t.one[e]){o=t.one[e];return;}var u=n.createElement(e);for(var a=0;a<i.length;a++){u.setAttribute(i[a].name,i[a].value);}t.structure[e]&&typeof _one[t.structure[e]]!="boolean"?t.one[t.structure[e]].appendChild(u):o&&o.appendChild&&(window.addEventListener||o.tagName!="NOSCRIPT")&&o.appendChild(u),s||(r.push(u),o=u);},end:function(e){r.length-=1,r.length>0?o=r[r.length-1]:o=t.one.body;},chars:function(e){if(window.addEventListener){var t=n.createTextNode(e);o.appendChild(t);}else{o.text=e;}},comment:function(e){}});}};return t;}),define("../src/utils",["../src/bootstrap","../vendor/htmlparser"],function(e,t){var n={getKwds:function(){var e=document.getElementsByTagName("meta"),t=[],n,r=e.length;for(n=0;n<r;n++){e[n].name&&e[n].name.toLowerCase()==="keywords"&&e[n].content!==""&&t.push(e[n].content);}return t.join(",");},getMeta:function(e){var t=document.getElementsByTagName("meta"),n=t.length;for(var r=0;r<n;r++){var i=t[r];if(i.name.toLowerCase()===e.toLowerCase()&&i.content!==""){return i.content;}}return null;},scriptWithOnload:function(e,t){var n=document.createElement("script");return n.src=e,n.onloadDone=!1,n.onload=function(){n.onloadDone||(n.onloadDone=!0,typeof t=="function"&&t());},n.onreadystatechange=function(){("loaded"===n.readyState||"complete"===n.readyState)&&!n.onloadDone&&(n.onloadDone=!0,typeof t=="function"&&t());},n;},isMobile:function(){var e=!1,t=["Mobile","Tablet","Handheld","Android","iPhone","Kindle","Silk","Nokia","Symbian","BlackBerry"];for(var n in t){if(navigator.userAgent.indexOf(t[n])!==-1){e=!0;break;}}return e;},isDebug:function(){var e=!1;return typeof window.location!="undefined"&&typeof window.location.search!="undefined"&&window.location.search.indexOf("debug=1")!==-1&&(e=!0),e;},addEvent:function(e,t,n){e.addEventListener?e.addEventListener(t,n,!1):e.attachEvent("on"+t,function(t){return n.call(e,t);});}};return window.BKTAG.htmlToDom=t.htmlToDom,window.BKTAG.util=n,n;}),define("../src/core",["../src/bootstrap","../src/utils"],function(e,t){var n=[],r=!1,i={site:"site_id",limit:"pixel_limit",excludeBkParams:"ignore_meta",excludeTitle:"exclude_title",excludeKeywords:"exclude_keywords",excludeReferrer:"exclude_referrer",excludeLocation:"exclude_location",partnerID:"partner_id",allowMultipleCalls:"allow_multiple_calls",callback:"callback",useImage:"use_image",useMultipleIframes:"use_multiple_iframes",allData:"all_data",timeOut:"timeout",ignoreOutsideIframe:"ignore_outside_iframe",eventScheduling:"event_scheduling",pixelUrl:"pixel_url",pixelSecure:"pixel_secure",metaVars:"meta_vars",jsList:"js_list",paramList:"param_list",useMobile:"use_mobile",disableMobile:"disable_mobile",isDebug:"is_debug",limitGetLength:"limit_get_length"},s={_dest:null,addParam:function(e,t,r){return typeof varMap!="undefined"&&varMap[t]&&(t=varMap[t]),typeof r!="undefined"?n.push(e+"="+encodeURIComponent(t+"="+r)):n.push(e+"="+t),BKTAG;},addBkParam:function(e,t){if(typeof e=="string"&&typeof t=="string"){s.addParam("phint","__bk_"+e,t);}else{for(var n in e){e.hasOwnProperty(n)&&typeof e[n]=="string"&&s.addParam("phint","__bk_"+n,e[n]);}}return BKTAG;},_reset:function(){r=!1,n=[];for(var e in i){i.hasOwnProperty(e)&&delete window["bk_"+i[e]];}return BKTAG;},params:function(){return n;},getGlobals:function(e){if(e.length){for(var t=0;t<e.length;t++){var n=e[t];typeof window[n]!="undefined"&&n!==""&&window[n]!==""&&bk_addPageCtx(n,window[n]);}}else{for(var r in e){e.hasOwnProperty(r)&&typeof r=="string"&&(typeof e[r]=="string"||typeof e[r]=="number"||typeof e[r]=="boolean")&&bk_addPageCtx(r,e[r]);}}},doTag:function(o,u,a,f,l,c,h,p,d){var v={site:o,limit:u,excludeBkParams:a,partnerID:f,allowMultipleCalls:l,callback:c,allData:h,timeOut:p,ignoreOutsideIframe:d};for(var m in i){i.hasOwnProperty(m)&&typeof window["bk_"+i[m]]!="undefined"&&(v[m]=window["bk_"+i[m]]);}if(typeof o=="object"){for(var g in i){i.hasOwnProperty(g)&&typeof o[i[g]]!="undefined"&&(v[g]=o[i[g]]);}}if(typeof r!="undefined"&&r&&v.allowMultipleCalls!==!0){return;}r=!0,v.timeOut===undefined&&(v.timeOut=1000),n.unshift("ret="+(v.callback?"js":"html"));var y=typeof v.partnerID!="undefined"&&v.partnerID!==null;y&&n.unshift("partner="+encodeURIComponent(v.partnerID));var b={2607:1,2834:1,2894:1,3316:1,3317:1,3318:1,3319:1,3321:1,3322:1,3323:1,3324:1,3325:1,3326:1,3327:1,3328:1,3329:1,3330:1,3331:1,3332:1,3333:1,3334:1,3338:1,3339:1,3340:1,3341:1,3344:1,3345:1,3346:1,3348:1};!v.excludeBkParams&&!v.excludeTitle&&document.title!==""&&s.addBkParam("t",document.title),!v.excludeBkParams&&!v.excludeKeywords&&s.addBkParam("k",t.getKwds()),!v.excludeBkParams&&!v.excludeReferrer&&"referrer" in document&&document.referrer!==""&&s.addBkParam("pr",document.referrer),!v.excludeBkParams&&!v.excludeLocation&&s.addBkParam("l",window.location.toString()),v.callback?s.addParam("jscb",encodeURIComponent(v.callback)):typeof v.limit!="undefined"&&s.addParam("limit",encodeURIComponent(v.limit)),v.allData===!0&&s.addParam("data","all"),v.disableMobile!==!0&&t.isMobile()&&typeof fortyone!="undefined"&&s.addParam("bkfpd",fortyone.collect()),v.eventScheduling===!0&&t.addEvent("message",function(e){if(e.origin!=="http://tags.bluekai.com"){return;}var t=document.getElementById("__bkframe"),n=function(e){return function(){t.contentWindow.postMessage(JSON.stringify({event:e}),"*"),t.contentWindow.postMessage(JSON.stringify({schedule:"run"}),"*");};},r=JSON.parse(e.data);r.status&&r.status==="loaded"&&t.contentWindow.postMessage(JSON.stringify({get:"events"}),"*");if(r.scheduled){var i=JSON.parse(r.scheduled);for(var s in i){var o=i[s]==="window"?window:document.getElementById(i[s]);o.addEventListener(s,n(s),!1);}}r.status&&r.status==="complete"&&t.contentWindow.postMessage(JSON.stringify({status:"ack"}),"*");},!1),(t.isDebug()||v.isDebug)&&s.addParam("debug","1"),!v.excludeBkParams&&typeof v.paramList!="undefined"&&s.getGlobals(v.paramList),!v.excludeBkParams&&typeof v.jsList!="undefined"&&s.getGlobals(v.jsList);if(!v.excludeBkParams&&typeof v.metaVars!="undefined"){for(var w=0;w<v.metaVars.length;w++){var E=t.getMeta(v.metaVars[w]);E!==null&&s.addBkParam(v.metaVars[w],E);}}s.addParam("r",parseInt(Math.random()*99999999,10));var S="https://stags.bluekai.com/",x="http://tags.bluekai.com/",T=("https:"===document.location.protocol?v.pixelSecure?v.pixelSecure:S:v.pixelUrl?v.pixelUrl:x)+(y?"psite":"site")+"/"+v.site,N=T+"?"+n.join("&");v.limitGetLength&&(N=N.substr(0,2000)),BKTAG._dest=s._dest=N;if(v.callback){if(v.useImage){var C=document.createElement("span");C.style.display="none";var k=document.getElementsByTagName("body")[0];k.appendChild(C);var L=document.createElement("img");L.src=s._dest,C.appendChild(L);}else{var A=document.createElement("script");A.type="text/javascript",A.src=s._dest,A.id="__bk_script__",b[""+o]&&setTimeout(function(){var e=document.getElementById("__bk_script__");e&&(e.removeNode?e.removeNode(!0):e.parentNode.removeChild(e));},v.timeOut),document.getElementsByTagName("head")[0].appendChild(A);}}else{e.checkFrame(function(){if(v.useMultipleIframes){var t=e.createFrame("__bkframe_"+v.site+"_"+(new Date).valueOf());t.className="__bkframe_site_"+v.site,t.src=N,document.getElementsByTagName("body")[0].appendChild(t);}else{if(frames&&frames.__bkframe){frames.__bkframe.location.replace(N);}else{var n=e.createFrame("__bkframe");document.getElementsByTagName("body")[0].appendChild(n),frames.__bkframe.location.replace(N);}}}),n.shift();if(typeof v.ignoreOutsideIframe!="undefined"&&v.ignoreOutsideIframe===!1){n.unshift("ret=jsht"),N=T+"?"+n.join("&"),N=N.substr(0,2000);var O=document.createElement("script");O.src=N,O.type="text/javascript",document.getElementsByTagName("body").item(0).appendChild(O);}}typeof u=="function"&&u(),n=[];}};for(var o in s){s.hasOwnProperty(o)&&(window.BKTAG[o]=s[o]);}return typeof window.bk_async=="function"&&window.setTimeout(function(){bk_async();},0),s;}),define("../src/aliases",["../src/core"],function(){window.BKTAG.addCtxParam=function(e,t){return BKTAG.addParam("phint",e,t),BKTAG;},window.BKTAG.addBkParam=function(e,t){return BKTAG.addParam("phint","__bk_"+e,t),BKTAG;},window.BKTAG.addPageCtx=window.bk_addPageCtx=window.BKTAG.addUserCtx=window.bk_addUserCtx=function(e,t){return BKTAG.addParam("phint",e,t),BKTAG;},window.BKTAG.doJSTag=window.bk_doJSTag=function(e,t,n){BKTAG.doTag(e,t,!1,null,n);},window.BKTAG.doJSTag2=window.bk_doJSTag2=function(e,t){BKTAG.doTag(e,t);},window.BKTAG.doCarsJSTag=window.bk_doCarsJSTag=function(e,t){BKTAG.doTag(e,t,!0);},window.BKTAG.doPartnerAltTag=window.bk_doPartnerAltTag=function(e,t,n){if(typeof n=="undefined"||n===null){n=0;}BKTAG.doTag(e,t,!1,n);},window.BKTAG.doCallbackTag=window.bk_doCallbackTag=function(e,t,n,r){BKTAG.doTag(e,0,!1,null,n,t,r);},window.BKTAG.doCallbackTagWithTimeOut=window.bk_doCallbackTagWithTimeOut=function(e,t,n,r,i){BKTAG.doTag(e,0,!1,null,n,t,r,i);},window.BKTAG.sendData=function(e){BKTAG.doTag(e);};}),define("mobile",["../vendor/fortyone","../vendor/json2","../src/core","../src/aliases"],function(){}),require("mobile");})();