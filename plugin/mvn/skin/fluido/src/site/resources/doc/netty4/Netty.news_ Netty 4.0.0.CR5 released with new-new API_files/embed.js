var DISQUS=function(h){var j=h.DISQUS||{};j.AssertionError=function(d){this.message=d};j.AssertionError.prototype.toString=function(){return"Assertion Error: "+(this.message||"[no message]")};j.assert=function(d,f,g){if(!d)if(g)h.console&&h.console.log("DISQUS assertion failed: "+f);else throw new j.AssertionError(f);};var g=[];j.define=function(d,f){typeof d==="function"&&(f=d,d="");for(var i=d.split("."),l=i.shift(),c=j,a=(f||function(){return{}}).call({overwrites:function(a){a.__overwrites__=!0;
return a}},h);l;)c=c[l]?c[l]:c[l]={},l=i.shift();for(var b in a)a.hasOwnProperty(b)&&(!a.__overwrites__&&c[b]!==null&&j.assert(!c.hasOwnProperty(b),"Unsafe attempt to redefine existing module: "+b,!0),c[b]=a[b],g.push(function(a,b){return function(){delete a[b]}}(c,b)));return c};j.use=function(d){return j.define(d)};j.cleanup=function(){for(var d=0;d<g.length;d++)g[d]()};return j}(window);
DISQUS.define(function(h,j){var g=h.DISQUS,d=h.document,f=d.getElementsByTagName("head")[0]||d.body,i={running:!1,timer:null,queue:[]},l=0;g.getUid=function(a){var e=++l+"";return a?a+e:e};g.defer=function(a,e){function k(){var a=i.queue;if(a.length===0)i.running=!1,clearInterval(i.timer);for(var b=0,e;e=a[b];b++)e[0]()&&(a.splice(b--,1),e[1]())}i.queue.push([a,e]);k();if(!i.running)i.running=!0,i.timer=setInterval(k,100)};g.isOwn=function(a,e){return Object.prototype.hasOwnProperty.call(a,e)};g.isString=
function(a){return Object.prototype.toString.call(a)==="[object String]"};g.each=function(a,e){var k=a.length,c=Array.prototype.forEach;if(isNaN(k))for(var d in a)g.isOwn(a,d)&&e(a[d],d,a);else if(c)c.call(a,e);else for(c=0;c<k;c++)e(a[c],c,a)};g.extend=function(a){g.each(Array.prototype.slice.call(arguments,1),function(e){for(var c in e)a[c]=e[c]});return a};g.serializeArgs=function(a){var e=[];g.each(a,function(a,b){a!==j&&e.push(b+(a!==null?"="+encodeURIComponent(a):""))});return e.join("&")};
g.serialize=function(a,e,c){e&&(a+=~a.indexOf("?")?a.charAt(a.length-1)=="&"?"":"&":"?",a+=g.serializeArgs(e));if(c)return e={},e[(new Date).getTime()]=null,g.serialize(a,e);e=a.length;return a.charAt(e-1)=="&"?a.slice(0,e-1):a};var c,a;"addEventListener"in h?(c=function(a,e,c){a.addEventListener(e,c,!1)},a=function(a,e,c){a.removeEventListener(e,c,!1)}):(c=function(a,e,c){a.attachEvent("on"+e,c)},a=function(a,e,c){a.detachEvent("on"+e,c)});g.require=function(b,e,k,m,i){function n(b){b=b||h.event;
if(!b.target)b.target=b.srcElement;if(b.type=="load"||/^(complete|loaded)$/.test(b.target.readyState))m&&m(),j&&clearTimeout(j),a(b.target,s,n)}var q=d.createElement("script"),s=q.addEventListener?"load":"readystatechange",j=null;q.src=g.serialize(b,e,k);q.async=!0;q.charset="UTF-8";(m||i)&&c(q,s,n);i&&(j=setTimeout(function(){i()},2E4));f.appendChild(q);return g};g.requireStylesheet=function(a,e,c){var m=d.createElement("link");m.rel="stylesheet";m.type="text/css";m.href=g.serialize(a,e,c);f.appendChild(m);
return g};g.requireSet=function(a,e,c){var d=a.length;g.each(a,function(a){g.require(a,{},e,function(){--d===0&&c()})})};g.injectCss=function(a){var e=d.createElement("style");e.setAttribute("type","text/css");a=a.replace(/\}/g,"}\n");h.location.href.match(/^https/)&&(a=a.replace(/http:\/\//g,"https://"));e.styleSheet?e.styleSheet.cssText=a:e.appendChild(d.createTextNode(a));f.appendChild(e)};g.isString=function(a){return Object.prototype.toString.call(a)==="[object String]"}});
DISQUS.define("JSON",function(){function h(a){return a<10?"0"+a:a}function j(a){l.lastIndex=0;return l.test(a)?'"'+a.replace(l,function(a){var e=b[a];return typeof e==="string"?e:"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)})+'"':'"'+a+'"'}function g(b,k){var d,m,i,h,l=c,p,o=k[b];o&&typeof o==="object"&&typeof o.toJSON==="function"&&!f&&(o=o.toJSON(b));typeof e==="function"&&(o=e.call(k,b,o));switch(typeof o){case "string":return j(o);case "number":return isFinite(o)?String(o):"null";case "boolean":case "null":return String(o);
case "object":if(!o)return"null";c+=a;p=[];if(Object.prototype.toString.apply(o)==="[object Array]"){h=o.length;for(d=0;d<h;d+=1)p[d]=g(d,o)||"null";i=p.length===0?"[]":c?"[\n"+c+p.join(",\n"+c)+"\n"+l+"]":"["+p.join(",")+"]";c=l;return i}if(e&&typeof e==="object"){h=e.length;for(d=0;d<h;d+=1)m=e[d],typeof m==="string"&&(i=g(m,o))&&p.push(j(m)+(c?": ":":")+i)}else for(m in o)Object.hasOwnProperty.call(o,m)&&(i=g(m,o))&&p.push(j(m)+(c?": ":":")+i);i=p.length===0?"{}":c?"{\n"+c+p.join(",\n"+c)+"\n"+
l+"}":"{"+p.join(",")+"}";c=l;return i}}var d={},f=!1;if(typeof Date.prototype.toJSON!=="function")Date.prototype.toJSON=function(){return isFinite(this.valueOf())?this.getUTCFullYear()+"-"+h(this.getUTCMonth()+1)+"-"+h(this.getUTCDate())+"T"+h(this.getUTCHours())+":"+h(this.getUTCMinutes())+":"+h(this.getUTCSeconds())+"Z":null},String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function(){return this.valueOf()};var i=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
l=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,c,a,b={"\u0008":"\\b","\t":"\\t","\n":"\\n","\u000c":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"},e;d.stringify=function(b,d,k){var m;a=c="";if(typeof k==="number")for(m=0;m<k;m+=1)a+=" ";else typeof k==="string"&&(a=k);if((e=d)&&typeof d!=="function"&&(typeof d!=="object"||typeof d.length!=="number"))throw Error("JSON.stringify");return g("",{"":b})};d.parse=function(a,e){function b(a,
c){var d,k,n=a[c];if(n&&typeof n==="object")for(d in n)Object.hasOwnProperty.call(n,d)&&(k=b(n,d),k!==void 0?n[d]=k:delete n[d]);return e.call(a,c,n)}var c,a=String(a);i.lastIndex=0;i.test(a)&&(a=a.replace(i,function(a){return"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)}));if(/^[\],:{}\s]*$/.test(a.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,"@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,"]").replace(/(?:^|:|,)(?:\s*\[)+/g,"")))return c=eval("("+a+")"),
typeof e==="function"?b({"":c},""):c;throw new SyntaxError("JSON.parse");};var k={a:[1,2,3]},m,p;if(Object.toJSON&&Object.toJSON(k).replace(/\s/g,"")==='{"a":[1,2,3]}')m=Object.toJSON;typeof String.prototype.evalJSON==="function"&&(k='{"a":[1,2,3]}'.evalJSON(),k.a&&k.a.length===3&&k.a[2]===3&&(p=function(a){return a.evalJSON()}));(function(){var a=[1,2,3];typeof a.toJSON==="function"&&(a=a.toJSON(),f=!(a&&a.length===3&&a[2]===3))})();if(f||!m||!p)return{stringify:d.stringify,parse:d.parse};return{stringify:m,
parse:p}});
DISQUS.define(function(){function h(a){for(l=1;a=j.shift();)a()}var j=[],g,d=document,f=d.documentElement,i=f.doScroll,l=/^loade|c/.test(d.readyState),c;d.addEventListener&&d.addEventListener("DOMContentLoaded",g=function(){d.removeEventListener("DOMContentLoaded",g,!1);h()},!1);i&&d.attachEvent("onreadystatechange",g=function(){/^c/.test(d.readyState)&&(d.detachEvent("onreadystatechange",g),h())});c=i?function(a){self!=top?l?a():j.push(a):function(){try{f.doScroll("left")}catch(b){return setTimeout(function(){c(a)},50)}a()}()}:
function(a){l?a():j.push(a)};return{domready:c}});
DISQUS.define("Events",function(){var h=function(a){var b=!1,e;return function(){if(b)return e;b=!0;e=a.apply(this,arguments);a=null;return e}},j=DISQUS.isOwn,g=Object.keys||function(a){if(a!==Object(a))throw new TypeError("Invalid object");var b=[],e;for(e in a)j(a,e)&&(b[b.length]=e);return b},d=[].slice,f={on:function(a,b,e){if(!l(this,"on",a,[b,e])||!b)return this;this._events=this._events||{};(this._events[a]||(this._events[a]=[])).push({callback:b,context:e,ctx:e||this});return this},once:function(a,
b,e){if(!l(this,"once",a,[b,e])||!b)return this;var c=this,d=h(function(){c.off(a,d);b.apply(this,arguments)});d._callback=b;return this.on(a,d,e)},off:function(a,b,e){var c,d,f,n,i,h,j,r;if(!this._events||!l(this,"off",a,[b,e]))return this;if(!a&&!b&&!e)return this._events={},this;n=a?[a]:g(this._events);i=0;for(h=n.length;i<h;i++)if(a=n[i],f=this._events[a]){this._events[a]=c=[];if(b||e){j=0;for(r=f.length;j<r;j++)d=f[j],(b&&b!==d.callback&&b!==d.callback._callback||e&&e!==d.context)&&c.push(d)}c.length||
delete this._events[a]}return this},trigger:function(a){if(!this._events)return this;var b=d.call(arguments,1);if(!l(this,"trigger",a,b))return this;var e=this._events[a],k=this._events.all;e&&c(e,b);k&&c(k,arguments);return this},stopListening:function(a,b,e){var c=this._listeners;if(!c)return this;var d=!b&&!e;typeof b==="object"&&(e=this);a&&((c={})[a._listenerId]=a);for(var f in c)c[f].off(b,e,this),d&&delete this._listeners[f];return this}},i=/\s+/,l=function(a,b,e,c){if(!e)return!0;if(typeof e===
"object"){for(var d in e)a[b].apply(a,[d,e[d]].concat(c));return!1}if(i.test(e)){e=e.split(i);d=0;for(var f=e.length;d<f;d++)a[b].apply(a,[e[d]].concat(c));return!1}return!0},c=function(a,b){var e,c=-1,d=a.length,f=b[0],n=b[1],i=b[2];switch(b.length){case 0:for(;++c<d;)(e=a[c]).callback.call(e.ctx);break;case 1:for(;++c<d;)(e=a[c]).callback.call(e.ctx,f);break;case 2:for(;++c<d;)(e=a[c]).callback.call(e.ctx,f,n);break;case 3:for(;++c<d;)(e=a[c]).callback.call(e.ctx,f,n,i);break;default:for(;++c<d;)(e=
a[c]).callback.apply(e.ctx,b)}};DISQUS.each({listenTo:"on",listenToOnce:"once"},function(a,b){f[b]=function(b,c,d){var f=this._listeners||(this._listeners={}),n=b._listenerId||(b._listenerId=DISQUS.getUid("l"));f[n]=b;typeof c==="object"&&(d=this);b[a](c,d,this);return this}});f.bind=f.on;f.unbind=f.off;return f});
DISQUS.define(function(h){function j(){throw Error(Array.prototype.join.call(arguments," "));}function g(a,b,e){if(a.addEventListener)a.addEventListener(b,e,!1);else if(a.attachEvent)a.attachEvent("on"+b,e);else throw Error("No event support.");}function d(a,b,e){e||(e=0);var c,d,k,f,i=0,g=function(){i=new Date;k=null;f=a.apply(c,d)};return function(){var m=new Date,h=b-(m-i);c=this;d=arguments;h<=0?(clearTimeout(k),k=null,i=m,f=a.apply(c,d)):k||(k=setTimeout(g,h+e));return f}}var f=h.document,i=
DISQUS.use("JSON"),l={};if(!(DISQUS.version&&DISQUS.version()==="2")){var c=DISQUS.isOwn;g(h,"message",function(a){var b,e;for(e in l)if(c(l,e)&&a.origin==l[e].origin){b=!0;break}if(b)switch(b=i.parse(a.data),(e=l[b.sender])||j("Message from our server but with invalid sender UID:",b.sender),b.scope){case "host":e.trigger(b.name,b.data);break;case "global":DISQUS.trigger(b.name,b.data);break;default:j("Message",b.scope,"not supported. Sender:",a.origin)}},!1);g(h,"hashchange",function(){DISQUS.trigger("window.hashchange",
{hash:h.location.hash})},!1);g(h,"resize",function(){DISQUS.trigger("window.resize")},!1);g(h,"scroll",d(function(){DISQUS.trigger("window.scroll")},250,50));g(f,"click",function(){DISQUS.trigger("window.click")});var a=function(b){b=b||{};this.state=a.INIT;this.uid=b.uid||DISQUS.getUid();this.origin=b.origin;this.target=b.target;this.window=null;l[this.uid]=this;this.on("ready",function(){this.state=a.READY},this);this.on("die",function(){this.state=a.KILLED},this)};DISQUS.extend(a,{INIT:0,READY:1,
KILLED:2});DISQUS.extend(a.prototype,DISQUS.Events);a.prototype.sendMessage=function(a,b){var e=function(a,b,e){return function(){DISQUS.postMessage(e.window,a,b)}}(i.stringify({scope:"client",name:a,data:b}),this.origin,this);if(this.isReady())e();else this.on("ready",e)};a.prototype.hide=function(){};a.prototype.show=function(){};a.prototype.url=function(){return DISQUS.serialize(this.target,{disqus_version:"1374542197"})+"#"+this.uid};a.prototype.destroy=function(){this.state=a.KILLED;this.off()};
a.prototype.isReady=function(){return this.state===a.READY};a.prototype.isKilled=function(){return this.state===a.KILLED};var b=function(b){a.call(this,b);this.windowName=b.windowName};DISQUS.extend(b.prototype,a.prototype);b.prototype.load=function(){this.window=h.open("",this.windowName||"_blank");this.window.location=this.url()};b.prototype.isKilled=function(){return a.prototype.isKilled()||this.window.closed};var e=function(b){a.call(this,b);this.styles=b.styles||{};this.role=b.role||"application";
this.container=b.container;this.elem=null};DISQUS.extend(e.prototype,a.prototype);e.prototype.load=function(){var a=this.elem=f.createElement("iframe");a.setAttribute("id","dsq"+this.uid);a.setAttribute("data-disqus-uid",this.uid);a.setAttribute("allowTransparency","true");a.setAttribute("frameBorder","0");this.role&&a.setAttribute("role",this.role);DISQUS.extend(a.style,this.styles)};e.prototype.getOffset=function(){for(var a=this.elem,b=a,e=0,c=0;b;)e+=b.offsetLeft,c+=b.offsetTop,b=b.offsetParent;
return{top:c,left:e,height:a.offsetHeight,width:a.offsetWidth}};e.prototype.hide=function(){this.elem.style.display="none"};e.prototype.show=function(){this.elem.style.display=""};e.prototype.destroy=function(){a.prototype.destroy.call(this);this.elem&&this.elem.parentNode&&this.elem.parentNode.removeChild(this.elem)};var k=function(a){e.call(this,a);this.styles=DISQUS.extend({width:"100%",border:"none",overflow:"hidden",height:"0"},a.styles||{})};DISQUS.extend(k.prototype,e.prototype);k.prototype.load=
function(a){var b=this;e.prototype.load.call(b);var c=b.elem;c.setAttribute("width","100%");c.setAttribute("src",this.url());g(c,"load",function(){b.window=c.contentWindow;a&&a()});(DISQUS.isString(this.container)?f.getElementById(this.container)||f.body:this.container).appendChild(c)};var m=function(a){e.call(this,a);this.contents=a.contents;this.styles=DISQUS.extend({width:"100%",border:"none",overflow:"hidden"},a.styles||{})};DISQUS.extend(m.prototype,e.prototype);m.prototype.load=function(){e.prototype.load.call(this);
var a=this.elem;a.setAttribute("scrolling","no");(DISQUS.isString(this.container)?f.getElementById(this.container)||f.body:this.container).appendChild(a);this.window=a.contentWindow;try{this.window.document.open()}catch(b){a.src="javascript:var d=document.open();d.domain='"+f.domain+"';void(0);"}this.document=this.window.document;this.document.write(this.contents);this.document.close();if(a=this.document.body){var c=this.elem.style;c.height=c.minHeight=c.maxHeight=a.offsetHeight+"px"}};m.prototype.show=
function(){this.elem.style.display="block"};m.prototype.click=function(a){g(this.document.body,"click",function(b){a(b)})};var p=DISQUS.extend({},DISQUS.Events);return{log:function(a){var b=f.getElementById("messages");if(b){var e=f.createElement("p");e.innerHTML=a;b.appendChild(e)}},version:function(){return"2"},on:p.on,off:p.off,trigger:p.trigger,throttle:d,postMessage:function(a,b,e){a.postMessage(b,e)},WindowBase:a,Popup:b,Iframe:e,Channel:k,Sandbox:m}}});
DISQUS.define("next.host.utils",function(h,j){function g(a){if(!a||a.substring(a.length-8)!=="embed.js")return null;for(var b=[/(https?:)?\/\/(www\.)?disqus\.com\/forums\/([\w_\-]+)/i,/(https?:)?\/\/(www\.)?([\w_\-]+)\.disqus\.com/i,/(https?:)?\/\/(www\.)?dev\.disqus\.org\/forums\/([\w_\-]+)/i,/(https?:)?\/\/(www\.)?([\w_\-]+)\.dev\.disqus\.org/i],e,c=b.length,d=0;d<c;d++)if((e=a.match(b[d]))&&e.length&&e.length===4)return e[3];return null}function d(a,b,e){var k,e=e||b;if(a===c)return"";h.getComputedStyle?
k=c.defaultView.getComputedStyle(a,null).getPropertyValue(b):a.currentStyle&&(k=a.currentStyle[b]?a.currentStyle[b]:a.currentStyle[e]);return k=="transparent"||k===""||k=="rgba(0, 0, 0, 0)"?d(a.parentNode,b,e):k||null}function f(a){function b(a){a=Number(a).toString(16);return a.length==1?"0"+a:a}if(a.substr(0,1)==="#")return a;var e=/.*?rgb\((\d+),\s*(\d+),\s*(\d+)\)/.exec(a);if(!e||e.length!==4)return"";var a=b(e[1]),c=b(e[2]),e=b(e[3]);return"#"+a+c+e}function i(a,b,e,k){DISQUS.isString(b)&&(b=
c.createElement(b));var f=null;b.style.visibility="hidden";a.appendChild(b);f=d(b,e,k);a.removeChild(b);return f}function l(a){return a.toLowerCase().replace(/^\s+|\s+$/g,"").replace(/['"]/g,"")}var c=h.document;return{getShortnameFromUrl:g,getForum:function(a,b){var e=a.getElementsByTagName("script"),c=e.length,d,b=b||g;for(c-=1;c>=0;c--)if(d=e[c],d=d.getAttribute?d.getAttribute("src"):d.src,d=b(d),d!==null)return d.toLowerCase();return null},isSSL:function(a){return a.toLowerCase()==="https:"},
guessThreadTitle:function(){for(var a=c.getElementsByTagName("h1"),b=c.title,e=b.length,d=b,f=0.6,i=0;i<a.length;i++)(function(a){var a=a.textContent||a.innerText,c;if(!(a===null||a===j)){c=0;for(var i=Array(b.length),g=0;g<=b.length;g++){i[g]=Array(a.length);for(var h=0;h<=a.length;h++)i[g][h]=0}for(g=0;g<b.length;g++)for(h=0;h<a.length;h++)b[g]==a[h]&&(i[g+1][h+1]=i[g][h]+1,i[g+1][h+1]>c&&(c=i[g+1][h+1]));c/=e;c>f&&(f=c,d=a)}})(a[i]);return d},getContrastYIQ:function(a){a.match("^rgb")&&(a=f(a).substr(1));
var b=parseInt(a.substr(0,2),16),e=parseInt(a.substr(2,2),16),a=parseInt(a.substr(4,2),16);return(b*299+e*587+a*114)/1E3},colorToHex:f,getElementStyle:i,getAnchorColor:function(a){var b=c.createElement("a");b.href=+new Date;return i(a,b,"color")},normalizeFontValue:l,isSerif:function(a){for(var a=i(a,"span","font-family","fontFamily").split(","),b={courier:1,times:1,"times new roman":1,georgia:1,palatino:1,serif:1},e,c=0;c<a.length;c++)if(e=l(a[c]),b.hasOwnProperty(e))return!0;return!1},getBrowserSupport:function(a){if(a.postMessage){if(!a.JSON)return a.navigator.appName===
"Microsoft Internet Explorer"?2:1}else return 1;return 0},getPermalink:function(){var a=h.location.hash;return(a=a&&a.match(/comment\-([0-9]+)/))&&a[1]}}});
DISQUS.define("next.host.app",function(h,j){var g=DISQUS.isOwn,d=DISQUS.extend,f={getRegistry:function(){var a=this._registry;return a?a:this._registry={}},register:function(a){this.getRegistry()[a.uid]=a},unregister:function(a){delete this.getRegistry()[a.uid]},listByKey:function(){return this.getRegistry()},list:function(){var a=this.getRegistry(),b=[],e;for(e in a)g(a,e)&&b.push(a[e]);return b},get:function(a){var b=this.getRegistry();if(g(b,a))return b[a];return null}},i=function(a){var b=this.constructor;
this.uid=DISQUS.getUid();b.register&&b.register(this);this.settings=a||{};a=[];b=this;do a.unshift(b),b=b.constructor.__super__;while(b);for(var e=0,c=a.length;e<c;e++){b=a[e];if(b.events)this.on(b.events,this);if(b.onceEvents)this.once(b.onceEvents,this)}};d(i.prototype,DISQUS.Events);i.prototype.destroy=function(){var a=this.constructor;this.off();this.stopListening();a.unregister&&a.unregister(this)};i.extend=function(a,b){var c=this,f;f=a&&g(a,"constructor")?a.constructor:function(){return c.apply(this,
arguments)};d(f,c,b);var i=function(){this.constructor=f};i.prototype=c.prototype;f.prototype=new i;a&&d(f.prototype,a);f.__super__=c.prototype;return f};var l=i.extend({frame:null,urls:null,origins:{insecure:"http://disqus.com",secure:"https://disqus.com"},getUrl:function(){return this.settings.useSSL?this.urls.secure:this.urls.insecure},getFrame:function(){var a=this.settings,b={target:this.getUrl(),origin:a.useSSL?this.origins.secure:this.origins.insecure,uid:this.uid};a.windowName?b.windowName=
a.windowName:b.container=this.settings.container||document.body;this.getFrameSettings&&(b=this.getFrameSettings(b));return new (b.windowName?DISQUS.Popup:DISQUS.Channel)(b)},init:function(){var a=this,b;a.trigger("beforeInit");a.frame=b=this.getFrame();a.listenTo(b,"all",function(c,d){a.trigger("frame:"+c,d,b)});a.trigger("change:frame",b);a.frame.load(function(){a.trigger("frameLoaded",b)});a.trigger("afterInit")},destroy:function(){var a=this.frame;a&&(this.stopListening(a),a.destroy());this.frame=
null;i.prototype.destroy.call(this)}});d(l,f);var c=l.extend({getUrl:function(){var a=this.settings,b=l.prototype.getUrl.call(this),c={f:a.forum,t_i:a.identifier,t_u:a.url||h.location.href,t_s:a.slug,t_e:a.title,t_d:a.documentTitle,t_t:a.title||a.documentTitle,t_c:a.category,s_o:a.sortOrder,c:a.useConman||j};if(a.unsupported)c.n_s=a.unsupported;return DISQUS.serialize(b,c)},getFrameSettings:function(a){a.role="complementary";return a},events:{afterInit:function(){this.trigger("loading.start")},frameLoaded:function(a){var b=
a.elem;this.settings.unsupported?(b.style.height="500px",b.setAttribute("scrolling","yes"),b.setAttribute("horizontalscrolling","no"),b.setAttribute("verticalscrolling","yes"),a.show()):this.settings.windowName||(b.setAttribute("scrolling","no"),b.setAttribute("horizontalscrolling","no"),b.setAttribute("verticalscrolling","no"))},"frame:ready":function(a,b){var c=this.settings,c={permalink:c.permalink,anchorColor:c.anchorColor,referrer:h.location.href,colorScheme:c.colorScheme,language:c.language,
typeface:c.typeface,remoteAuthS3:c.remoteAuthS3,apiKey:c.apiKey,sso:c.sso,parentWindowHash:h.location.hash};if(b.elem&&h.navigator.userAgent.match(/(iPad|iPhone|iPod)/))c.width=b.elem.offsetWidth;b.sendMessage("init",c);this.trigger("loading.init")},"frame:resize":function(a,b){if(b.elem&&this.rendered)b.elem.style.height=a.height+"px",b.sendMessage("embed.resized"),this.scrollListener&&this.scrollListener()},"frame:scrollTo":function(a,b){if(b.elem&&b.getOffset){var c=b.getOffset(),c=a.relative===
"window"?a.top:c.top+a.top,d=this.getWindowYCoords();(a.force||!(c>d.pageYOffset&&c<d.pageYOffset+d.innerHeight))&&h.scrollTo(0,c)}},"frame:mainViewRendered":function(a,b){this.rendered=!0;b.trigger("resize",a);b.sendMessage("embed.rendered");this.trigger("loading.done")},"frame:fail":function(a,b){if(b.elem)b.elem.style.height="75px";this.trigger("fail",a)}}});return{expose:function(a,b,c){DISQUS.each(b,function(b){c[b]=function(){return a[b].apply(a,arguments)}})},BaseApp:i,WindowedApp:l,ThreadBoundApp:c,
PublicInterfaceMixin:f}});
DISQUS.define("next.host.profile",function(h){var j=DISQUS.next.host.app.WindowedApp,g=j.extend({urls:{insecure:"http://disqus.com/embed/profile/",secure:"https://disqus.com/embed/profile/"},events:{beforeInit:function(){var d=this.settings;d.fullscreen=d.fullscreen!==!1},afterInit:function(){this.trigger("loading.start")},"frame:ready":function(d,f){f.sendMessage("init",{referrer:h.location.href,fullscreen:this.settings.fullscreen});this.trigger("loading.init")},"frame:close":function(d,f){f.hide();
h.focus()},"frame:renderProfile":function(d){this.trigger("renderProfile",d)}},getUrl:function(){var d=this.settings,f=j.prototype.getUrl.call(this);return DISQUS.serialize(f,{f:d.forum,language:d.language})},getFrameSettings:function(d){var f=this.settings.fullscreen;d.role="dialog";d.styles=f?{height:"100%",position:"fixed",top:0,left:0,zIndex:999999}:{height:"100%",padding:0};return d},show:function(d){DISQUS.isString(d)&&(d={username:d});var f=this.frame;if(!f.isReady())return void this.once("frame:ready",
function(){this.show(d)},this);f.sendMessage("showProfile",d);f.show()}});return{Profile:function(d){return new g(d)}}});
DISQUS.define("next.host.backplane",function(){var h;try{localStorage.setItem("disqus.localStorageTest","disqus"),localStorage.removeItem("disqus.localStorageTest"),h=!0}catch(j){h=!1}var g=function(d){this.frame=d;this.credentials="unset";var f=this;typeof Backplane==="function"&&typeof Backplane.version==="string"&&typeof Backplane.subscribe==="function"&&h&&Backplane(function(){f.initialize()})};DISQUS.extend(g.prototype,{frameEvents:{invalidate:"clearCredentials"},initialize:function(){var d=
this;DISQUS.each(this.frameEvents,function(f,i){d.frame.on("backplane."+i,typeof f==="function"?f:d[f],d)});this.credentialsFromLocalStorage()&&this.frame.sendMessage("login",{backplane:this.credentials});this.subscribe()},subscribe:function(){var d=this;Backplane.subscribe(function(f){var i=d.handlers[f.type];i&&i.call(d,f)})},handlers:{"identity/login":function(d){var f=d.messageURL,d=d.channel;this.credentials!=="unset"&&this.credentials!==null&&this.credentials.channel===d&&this.credentials.messageUrl===
f||(this.setCredentials(d,f),this.frame.sendMessage("login",{backplane:this.getCredentials()}))}},credentialsFromLocalStorage:function(){var d=localStorage.getItem("disqus.backplane.channel"),f=localStorage.getItem("disqus.backplane.messageUrl");this.setCredentials(d,f,!0);return this.credentials},setCredentials:function(d,f,i){if(!d||!f)return void this.clearCredentials();i||(localStorage.setItem("disqus.backplane.channel",d),localStorage.setItem("disqus.backplane.messageUrl",f));this.credentials=
{channel:d,messageUrl:f}},getCredentials:function(){if(this.credentials!=="unset")return this.credentials;return this.credentialsFromLocalStorage()},clearCredentials:function(d){d=d||{};this.credentials=null;localStorage.removeItem("disqus.backplane.channel");localStorage.removeItem("disqus.backplane.messageUrl");if(d.redirectUrl)window.location=d.redirectUrl}});return{BackplaneIntegration:g}});
DISQUS.define("next.host.lounge",function(h){var j=h.document,g=DISQUS.next.host,d=g.app.ThreadBoundApp,f=g.profile.Profile,i=d.extend({indicators:null,wasInViewport:!1,wasNearViewport:!1,urls:{insecure:"http://disqus.com/embed/comments/",secure:"https://disqus.com/embed/comments/"},events:{beforeInit:function(){var c=this.settings,a=!c.windowName;if(!c.unsupported)this.indicators={},a&&(this.addLoadingAnim(),this.listenTo(DISQUS,"window.scroll",this.scrollListener)),this.bindPublisherCallbacks(),
this.forwardGlobalEvents()},"frame:reload":function(){h.location.reload()},"frame:reset":function(){DISQUS.reset({reload:!0})},"frame:session.identify":function(c){this.trigger("session.identify",c)},"frame:posts.paginate":function(){this.trigger("posts.paginate")},"frame:posts.create":function(c){this.trigger("posts.create",{id:c.id,text:c.raw_message})},"frame:profile.show":function(c){var a=this.settings,b=this.profile;if(!b||b.frame.isKilled())b=this.profile=f({windowName:c.windowName,language:c.language,
useSSL:a.useSSL,forum:a.forum}),b.init();b.show({id:c.userId})},"frame:realtime.init":function(c,a){if(a.getOffset){var b=["north","south"],e,d;d=a.getOffset().width+"px";for(var f={width:d,minWidth:d,maxWidth:d,position:"fixed"},i={north:{top:"0"},south:{bottom:"0"}},g=0;g<b.length;g++)d=b[g],e=new DISQUS.Sandbox({uid:"-indicator-"+d,container:this.settings.container,contents:c[d].contents,styles:DISQUS.extend(i[d],f),role:"alert"}),e.load(),e.hide(),function(b){e.click(function(){a.sendMessage("realtime.click",
b)})}(d),this.indicators[d]=e;this.on({"frame:realtime.showNorth":function(a){var b=this.indicators.north;b.document.getElementById("message").innerHTML=a;b.show()},"frame:realtime.hideNorth":function(){this.indicators.north.hide()},"frame:realtime.showSouth":function(a){var b=this.indicators.south;b.document.getElementById("message").innerHTML=a;b.show()},"frame:realtime.hideSouth":function(){this.indicators.south.hide()}},this)}},"fail loading.done":function(){this.removeLoadingAnim()}},onceEvents:{"frame:loadLinkAffiliator":function(c,
a){var b;if(!h.vglnk_self&&!h.vglnk&&!function(){for(var a in h)if(a.indexOf("skimlinks")===0||a.indexOf("skimwords")===0)return!0;return!1}()){var e=c.apiUrl,d=c.key,f=String(c.id);if(!(c.clientUrl==null||e==null||d==null||c.id==null))DISQUS.define("vglnk",function(){return{api_url:e,key:d,sub_id:f}}),h.vglnk_self="DISQUS.vglnk",DISQUS.require(c.clientUrl),DISQUS.defer(function(){return DISQUS.vglnk.opt},function(){a.sendMessage("affiliationOptions",{timeout:DISQUS.vglnk.opt("click_timeout")})}),
this.listenForAffiliationRequests(e,d,f)}},"frame:loadBackplane":function(c,a){this.backplane=new DISQUS.next.host.backplane.BackplaneIntegration(a)}},listenForAffiliationRequests:function(c,a,b){var e=this.frame;this.on("frame:affiliateLink",function(d){var f=DISQUS.vglnk.$;if(!f)return void e.sendMessage("affiliateLink");f.request(c+"/click",{format:"jsonp",out:d.url,key:a,loc:e.target,subId:b},{fn:function(a){return function(b){var c={token:a};if(b)c.url=b;e.sendMessage("affiliateLink",c)}}(d.token),
timeout:DISQUS.vglnk.opt("click_timeout")})})},forwardGlobalEvents:function(){var c=this;c.settings.windowName||(c.listenTo(DISQUS,"window.resize",function(){c.frame.sendMessage("window.resize")}),c.listenTo(DISQUS,"window.click",function(){c.frame.sendMessage("window.click")}));c.listenTo(DISQUS,"window.hashchange",function(a){c.frame.sendMessage("window.hashchange",a.hash)})},bindPublisherCallbacks:function(){var c=this,a=i.LEGACY_EVENTS_MAPPING,b=c.settings.callbacks;b&&DISQUS.each(b,function(b,
d){a[d]&&DISQUS.each(b,function(b){c.on(a[d],b)})})},addLoadingAnim:function(){var c,a,b,d=this.settings.container,f=j.createElement("style");f.type="text/css";f.styleSheet?f.styleSheet.cssText=".disqus-loader{animation:disqus-embed-spinner .7s infinite linear;-webkit-animation:disqus-embed-spinner .7s infinite linear}@keyframes disqus-embed-spinner{0%{transform:rotate(0deg)}100%{transform:rotate(360deg)}}@-webkit-keyframes disqus-embed-spinner{0%{-webkit-transform:rotate(0deg)}100%{-webkit-transform:rotate(360deg)}}":
f.appendChild(j.createTextNode(".disqus-loader{animation:disqus-embed-spinner .7s infinite linear;-webkit-animation:disqus-embed-spinner .7s infinite linear}@keyframes disqus-embed-spinner{0%{transform:rotate(0deg)}100%{transform:rotate(360deg)}}@-webkit-keyframes disqus-embed-spinner{0%{-webkit-transform:rotate(0deg)}100%{-webkit-transform:rotate(360deg)}}"));c=j.createElement("div");a=j.createElement("div");b=j.createElement("div");a.appendChild(f);a.appendChild(b);c.appendChild(a);c.style.overflow=
"hidden";a.style.height=a.style.width="54px";a.style.margin="0 auto";a.style.overflow="hidden";b.style.height=b.style.width="29px";b.style.margin="11px 14px";b.className="disqus-loader";b.style.backgroundImage=a.style.backgroundImage="url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFMAAABmCAMAAACA210sAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAadEVYdFNvZnR3YXJlAFBhaW50Lk5FVCB2My41LjEwMPRyoQAAAlhQTFRFMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtMzY63+TtWDj2BwAAAMh0Uk5TAAABAQICAwMEBAUFBgYHBwgICQkKCgsLDAwNDQ4ODw8QEBEREhITExQUFRUWFhcXGBgZGRoaGxscHB0dHh4fHyAgISEjIyQkJSUmJigoKSkqKisrLCwtLS4uLy8wMDExMjIzMzQ0NTU2Njc3ODg5OTo6Ozs8PD09Pj4/P0BAQUFCQkNDRERFRUZGR0dISElJS0tMTE1NTk5PT1BQUVFSUlNTVFRVVVZWV1dYWFlZWlpbW1xcXV1eXl9fYGBhYWJiY2NkZGVlZmaMInkiAAAFVklEQVRo3u2Yf0hbVxTHP/flmYYQxFoXghMJzkpXQmszCd0ma7eKKyKuSOlKGaV0RaRzsIHIGDK2MqSsXVvKcKWIG6V0ItI5Z1tXZBsiIkWciC0iYsMIIbisC1mWhpDG/RFjfpvr4O2vnH+Se+/J575z3z3n5n4F2Uy12aotpUY1HFp1OecXo+S00vo6q9kYXF2ZnfLG+0QWYH2jw5jcEZi6P50dW33qoBL//sl4TqbpyPHSzB97bo2GMjr1bcfUjcbcWXIwlZb24uxBenvH0sM+b0s0om2PAJPBC+hS3KxfvrUtx8IZD+z9LZDcUfL1zqTWj98DtLVP/JP2nIc74+vonplfWQ2EDcZya22deb3T1zOZtOq9saf0jMy6vSarywdU3tAvdIRTmGdOxz5Dd8cWkqeyNzXG1i16ZWijs+MEQPjaUCTh+EU9DFxNZna2AhAZvPUkPXDLqebY++3rj7/xfhXwfTyX5LX/EhA9tZxYz/bjACx0/vQ0YzEDk9O27QD2vx/Gej6oBiKdcylub24DsWN8g9ncAcDAp39mfUV/jJp3Aux//Big9CMF+Go8xcfveR14/k6cuatHB0Qvf5MrZ55NiH0Ajl/8wKEDgOezNOeVV8tAca8z9Zd3AFy4nTsLmRX7AP2Ld9bgxAvA4Ey6S9HLwNP11HrHCnBjmM2sbwTA1gJUAWQgmQKwxpiWkwAz19ncriwBtJugDMCZ4eAOAeYY84QeCPZE8zBDn0eA4qOgAvgzPQKASQEobQbo85DPlgcBjhkIA2SpNSVAQAFoMADeYfLbzSBQ8hpPACwZw2YV8CoATQCDIQmmbwTgMCsA9oxhO4BTASw1QGQMGRsBqDPNADSr6aPNADNKnD7rlWI6lwG1bioCVDSlDb5iB6ITClAL8AA5mwbY9eTXjaRPWs1ugEmvEt/AjySZjwCs9EcAQ3tK8bpUAkT7UYBKgGVJphOgAucAwI2kgfq+KoChJVRQTYA/IMl0AZTB9Vob9+cBSn1RxeJorAVg6RqoYIwngJRFQgYwQqSr19ILUH6LkCl+Iru6QglmEGkosdT0nXWsApzVo4+PLXZ5Y6NBWAdLmbrOxXcfoO6NxHk81BsmiVksjTSkRKV0bBAnvl3amDXiLwZTsV+OWQmQyA/9UF2V2Rj0OmcnvMmROPcAVXNbYLoS5W90NMNFAX4HsEnGvjt7NU5nLgA4JJn78yedEj9XasukkDXVQGQ2L9O9DKhNUswmgJlAXiZjAEdUCWRJC8Bd8jP9AOGIBPOkAfBNSjBbAW7LrOZRgIFQfubuXUBI4uwwdKuAL9/sCnAE4GeJPIrV9WuB/MziBgCJk7i9BWB+VKLKHDYAiwuo5RUW9/QmyJMAgXNRCWYrgP5qhVmBQHeus87Y1QhAjztvPDocbwNsLzcJQN+oPsy6p2wXXgLg4j0kmO9Zk9ui9tBfzrV0r/L3P9wOwPXvJHacKLudkT/O4fHkW4Jib2pYv3dclPlPhTh9Jl6nPW7vQcP698W5RddqMGQyVpbb7fHqEjg3KVUTxLA56Ha7XB63OwKV3ZuU0QfnPXLlUOxxpcTZ0laS4755dVz2yMq4Fxtbj2WppO6bd8P8Zyao9Q0OU8q9Z3p8Koq8CZF1h9lqaixlRn0w5HWtLCytsSVTc8y0rahIpxNFz3S6Ip1Y26J2ITTQLoQG2oXQQLsQGmgXQgPtQmigXQgNtAuhgXYhNNAuhAbahShoFwXtoqBdFLSLgnZR0C4K2kVBuyhoFwXtIofOIDTQLoQG2sVzGmgX72qgXfyggXaxVwPtQmigXYj/S7tQU7WLrQCBfwFnft8xK3413wAAAABJRU5ErkJggg==)";
b.style.backgroundRepeat=a.style.backgroundRepeat="no-repeat";b.style.backgroundPosition="-54px 0";if(this.settings.colorScheme==="dark")a.style.backgroundPosition="0 -51px",b.style.backgroundPosition="-54px -51px";d.appendChild(c);this.loadingElem=c;this.timeout=setTimeout(function(){if(c)(new Image).src=DISQUS.serialize("//juggler.services.disqus.com/stat.gif",{event:"slow_embed"}),a.insertAdjacentHTML("afterend",'<p align="center">Disqus seems to be taking longer than usual. <a href="#" onclick="DISQUS.reset({reload: true}); return false;">Reload</a>?</p>')},
15E3)},removeLoadingAnim:function(){var c=this.loadingElem,a=this.settings.container;if(this.timeout)h.clearTimeout(this.timeout),this.timeout=null;c&&c.parentNode===a&&a.removeChild(c)},destroy:function(){var c=this.indicators;this.removeLoadingAnim();this.profile&&this.profile.destroy();if(c&&c.north)c.north.destroy(),c.north=null;if(c&&c.south)c.south.destroy(),c.south=null;d.prototype.destroy.call(this)},getWindowYCoords:function(){if(typeof h.pageYOffset=="number")this.getWindowScroll=function(){return h.pageYOffset},
this.getWindowHeight=function(){return h.innerHeight};else{var c=h.document,c=c.documentElement.clientHeight||c.documentElement.clientWidth?c.documentElement:c.body;this.getWindowScroll=function(){return c.scrollTop};this.getWindowHeight=function(){return c.clientHeight}}this.getWindowYCoords=function(){return{pageYOffset:this.getWindowScroll(),innerHeight:this.getWindowHeight()}};return this.getWindowYCoords()},scrollListener:function(){var c=this.frame,a=c.getOffset(),b=a.top,d=b+a.height,f=this.getWindowYCoords(),
i=f.pageYOffset,f=f.innerHeight,g=i+f,h=!1,j=!1;b<=g+f&&(j=(h=d>=i)&&b<=g);h&&(c.sendMessage("window.scroll",{frameOffset:a,pageOffset:i,height:f}),this.wasNearViewport||c.sendMessage("window.nearViewport"));this.wasNearViewport=h;if(j!==this.wasInViewport)c.sendMessage(j?"window.inViewport":"window.scrollOffViewport"),this.wasInViewport=j}},{LEGACY_EVENTS_MAPPING:{onReady:"loading.done",onNewComment:"posts.create",onPaginate:"posts.paginate",onIdentify:"session.identify"}}),l=function(c){return new i(c)};
g.app.expose(i,["list","listByKey","get"],l);return{Lounge:l}});DISQUS.define("next.host.ignition",function(){var h=DISQUS.next.host,j=h.lounge.Lounge,g=h.app.ThreadBoundApp.extend({urls:{insecure:"http://disqus.com/embed/ignition/",secure:"https://disqus.com/embed/ignition/"},events:{"frame:ignite":function(d){var f=this.lounge;if(!f||f.frame.isKilled())d=DISQUS.extend({windowName:d.windowName},this.settings),this.lounge=f=j(d),f.init()}}});return{Ignition:function(d){return new g(d)}}});
DISQUS.define("next.host.config",function(h,j){var g=DISQUS.use("next.host.utils"),d=function(d,f){this.win=d;this.configurator=f;this.config={page:{url:j,title:j,slug:j,category_id:j,identifier:j,language:j,api_key:j,remote_auth_s3:j,author_s3:j},strings:j,sso:{},callbacks:{preData:[],preInit:[],onInit:[],afterRender:[],onReady:[],onNewComment:[],preReset:[],onPaginate:[],onIdentify:[]}}};d.DISQUS_GLOBALS=["shortname","identifier","url","title","category_id","slug"];var f=d.prototype;f.getContainer=
function(){var d=this.win;return d.document.getElementById(d.disqus_container_id||"disqus_thread")};f.runConfigurator=function(){var d=this.configurator||this.win.disqus_config;if(typeof d==="function")try{d.call(this.config)}catch(f){}};f.getValuesFromGlobals=function(){var f=this.win,h=this.config,c=h.page,a;DISQUS.each(d.DISQUS_GLOBALS,function(a){var d=f["disqus_"+a];typeof d!=="undefined"&&(c[a]=d)});this.runConfigurator();if(!h.forum)a=c.shortname,h.forum=a?a.toLowerCase():g.getForum(f.document);
h.demo=f.disqus_demo};f.toJSON=function(){var d=this.win,f=this.config,c=f.page,a=this.getContainer();this.getValuesFromGlobals();return{container:a,forum:f.forum,sortOrder:"default",permalink:g.getPermalink(),useSSL:g.isSSL(d.location.protocol),language:f.language,typeface:g.isSerif(a)?"serif":"sans-serif",anchorColor:g.getAnchorColor(a),colorScheme:128<g.getContrastYIQ(g.getElementStyle(a,"span","color"))?"dark":"light",url:c.url||d.location.href.replace(/#.*$/,""),title:c.title,documentTitle:g.guessThreadTitle(),
slug:c.slug,category:c.category_id,identifier:c.identifier,apiKey:c.api_key,remoteAuthS3:c.remote_auth_s3,sso:f.sso,useConman:f.demo,unsupported:g.getBrowserSupport(d),callbacks:f.callbacks}};return{HostConfig:d}});
(function(h,j){function g(c){f.configurator=c;c=f.toJSON();if(!l)c.container.innerHTML="",l=!0;i=!c.unsupported&&h.disqus_ignition?d.ignition.Ignition(c):d.lounge.Lounge(c);i.init()}var d=DISQUS.use("next.host"),f=new d.config.HostConfig(h),i,l=!1;g();DISQUS.domready(function(){if(j.getElementsByClassName){var c=j.getElementsByClassName("dsq-brlink");c&&c.length&&c[0].parentNode.removeChild(c[0])}});DISQUS.request={get:function(c,a,b){DISQUS.require(c,a,b)}};DISQUS.reset=function(c){c=c||{};i&&(i.destroy(),
i=null);c.reload&&g(c.config)}})(this,this.document);