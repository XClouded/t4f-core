/*1364197871,173217599*/

if (self.CavalryLogger) { CavalryLogger.start_js(["AHQzl"]); }

__d("sprintf",[],function(a,b,c,d,e,f){function g(h,i){i=Array.prototype.slice.call(arguments,1);var j=0;return h.replace(/%s/g,function(k){return i[j++];});}e.exports=g;});
__d("Log",["sprintf"],function(a,b,c,d,e,f){var g=b('sprintf'),h={DEBUG:3,INFO:2,WARNING:1,ERROR:0};function i(k,l){var m=Array.prototype.slice.call(arguments,2),n=g.apply(null,m),o=window.console;if(o&&j.level>=k)o[l in o?l:'log'](n);}var j={level:-1,Level:h,debug:i.bind(null,h.DEBUG,'debug'),info:i.bind(null,h.INFO,'debug'),warn:i.bind(null,h.WARNING,'debug'),error:i.bind(null,h.ERROR,'debug')};e.exports=j;});
__d("resolveWindow",[],function(a,b,c,d,e,f){function g(h){var i=window,j=h.split('.');try{for(var l=0;l<j.length;l++){var m=j[l],n=/^frames\[['"]?([a-zA-Z0-9\-_]+)['"]?\]$/.exec(m);if(n){i=i.frames[n[1]];}else if(m==='opener'||m==='parent'||m==='top'){i=i[m];}else return null;}}catch(k){return null;}return i;}e.exports=g;});
__d("XD",["function-extensions","Arbiter","DOM","DOMDimensions","Log","URI","copyProperties","isInIframe","resolveWindow"],function(a,b,c,d,e,f){b('function-extensions');var g=b('Arbiter'),h=b('DOM'),i=b('DOMDimensions'),j=b('Log'),k=b('URI'),l=b('copyProperties'),m=b('isInIframe'),n=b('resolveWindow'),o='fb_xdm_frame_'+location.protocol.replace(':',''),p={_callbacks:[],_opts:{autoResize:false,allowShrink:true,channelUrl:null,hideOverflow:false,resizeTimeout:1000,resizeWidth:false,expectResizeAck:false,resizeAckTimeout:6000},_lastResizeAckId:0,_resizeCount:0,_resizeTimestamp:0,_shrinker:null,init:function(r){this._opts=l(l({},this._opts),r);if(this._opts.autoResize)this._startResizeMonitor();g.subscribe('Connect.Unsafe.resize.ack',function(s,t){if(!t.id)t.id=this._resizeCount;if(t.id>this._lastResizeAckId)this._lastResizeAckId=t.id;}.bind(this));},send:function(r,s){s=s||this._opts.channelUrl;if(!s)return;var t={},u=new k(s);l(t,r);l(t,k.explodeQuery(u.getFragment()));var v=new k(t.origin),w=v.getDomain()+(v.getPort()?':'+v.getPort():''),x=n(t.relation.replace(/^parent\./,'')),y=50,z=function(){var aa=x.frames[o];try{aa.proxyMessage(k.implodeQuery(t),[w]);}catch(ba){if(--y){setTimeout(z,100);}else j.warn('No such frame "'+o+'" to proxyMessage to');}};z();},_computeSize:function(){var r=i.getDocumentDimensions(),s=0;if(this._opts.resizeWidth){var t=document.body;if(t.clientWidth<t.scrollWidth){s=r.width;}else{var u=t.childNodes;for(var v=0;v<u.length;v++){var w=u[v],x=w.offsetLeft+w.offsetWidth;if(x>s)s=x;}}s=Math.max(s,p.forced_min_width);}r.width=s;if(this._opts.allowShrink){if(!this._shrinker)this._shrinker=h.create('div');h.appendContent(document.body,this._shrinker);r.height=Math.max(this._shrinker.offsetTop,0);}return r;},_startResizeMonitor:function(){var r,s=document.documentElement;if(this._opts.hideOverflow){s.style.overflow='hidden';document.body.style.overflow='hidden';}var t=(function(){var u=this._computeSize(),v=Date.now(),w=this._lastResizeAckId<this._resizeCount&&(v-this._resizeTimestamp)>this._opts.resizeAckTimeout;if(!r||(this._opts.expectResizeAck&&w)||(this._opts.allowShrink&&r.width!=u.width)||(!this._opts.allowShrink&&r.width<u.width)||(this._opts.allowShrink&&r.height!=u.height)||(!this._opts.allowShrink&&r.height<u.height)){r=u;this._resizeCount++;this._resizeTimestamp=v;var x={type:'resize',height:u.height,ackData:{id:this._resizeCount}};if(u.width&&u.width!=0)x.width=u.width;try{if(k(document.referrer).isFacebookURI()&&m()&&window.name&&window.parent.location&&window.parent.location.toString&&k(window.parent.location).isFacebookURI()){var z=window.parent.document.getElementsByTagName('iframe');for(var aa=0;aa<z.length;aa=aa+1)if(z[aa].name==window.name){if(this._opts.resizeWidth)z[aa].style.width=x.width+'px';z[aa].style.height=x.height+'px';}}this.send(x);}catch(y){this.send(x);}}}).bind(this);t();setInterval(t,this._opts.resizeTimeout);}},q=l({},p);e.exports.UnverifiedXD=q;e.exports.XD=p;a.UnverifiedXD=q;a.XD=p;});
__d("legacy:connect-xd",["XD"],function(a,b,c,d){a.UnverifiedXD=b('XD').UnverifiedXD;a.XD=b('XD').XD;},3);
__d("ArbiterFrame",[],function(a,b,c,d,e,f){var g={inform:function(h,i,j){var k=parent.frames,l=k.length,m;i.crossFrame=true;for(var n=0;n<l;n++){m=k[n];try{if(!m||m==window)continue;if(m.require){m.require('Arbiter').inform(h,i,j);}else if(m.AsyncLoader)m.AsyncLoader.wakeUp(h,i,j);}catch(o){}}}};e.exports=g;});
__d("Plugin",["Arbiter","ArbiterFrame"],function(a,b,c,d,e,f){var g=b('Arbiter'),h=b('ArbiterFrame'),i={CONNECT:'platform/plugins/connect',DISCONNECT:'platform/plugins/disconnect',ERROR:'platform/plugins/error',connect:function(j,k){var l={identifier:j,href:j,story_fbid:k};g.inform(i.CONNECT,l);h.inform(i.CONNECT,l);},disconnect:function(j,k){var l={identifier:j,href:j,story_fbid:k};g.inform(i.DISCONNECT,l);h.inform(i.DISCONNECT,l);},error:function(j,k){g.inform(i.ERROR,{action:j,content:k});}};e.exports=i;});
__d("PluginOptin",["DOMEvent","DOMEventListener","PluginMessage","PopupWindow","URI","UserAgent","copyProperties"],function(a,b,c,d,e,f){var g=b('DOMEvent'),h=b('DOMEventListener'),i=b('PluginMessage'),j=b('PopupWindow'),k=b('URI'),l=b('UserAgent'),m=b('copyProperties');function n(o){m(this,{return_params:k.getRequestURI().getQueryData(),login_params:{},optin_params:{},plugin:o});this.addReturnParams({ret:'optin'});delete this.return_params.hash;}m(n.prototype,{addReturnParams:function(o){m(this.return_params,o);return this;},addLoginParams:function(o){m(this.login_params,o);return this;},addOptinParams:function(o){m(this.optin_params,o);return this;},start:function(){var o=new k('/dialog/plugin.optin').addQueryData(this.optin_params).addQueryData({app_id:127760087237610,secure:k.getRequestURI().isSecure(),social_plugin:this.plugin,return_params:JSON.stringify(this.return_params),login_params:JSON.stringify(this.login_params)});if(l.mobile()){o.setSubdomain('m');}else o.addQueryData({display:'popup'});this.popup=j.open(o.toString(),420,450);i.listen();return this;}});n.starter=function(o,p,q,r){var s=new n(o);s.addReturnParams(p||{});s.addLoginParams(q||{});s.addOptinParams(r||{});return s.start.bind(s);};n.listen=function(o,p,q,r,s){h.add(o,'click',function(t){new g(t).kill();n.starter(p,q,r,s)();});};e.exports=n;});
__d("PluginConnectButton",["Arbiter","CSS","DOM","DOMDimensions","DOMEvent","DOMEventListener","DOMPosition","Form","Plugin","PluginOptin","Style","copyProperties","csx","cx"],function(a,b,c,d,e,f){var g=b('Arbiter'),h=b('CSS'),i=b('DOM'),j=b('DOMDimensions'),k=b('DOMEvent'),l=b('DOMEventListener'),m=b('DOMPosition'),n=b('Form'),o=b('Plugin'),p=b('PluginOptin'),q=b('Style'),r=b('copyProperties'),s=b('csx'),t=b('cx'),u=g.SUBSCRIBE_NEW,v=g.subscribe,w=function(y,z){return l.add(y,'click',z);};function x(y){this.config=y;var z=i.find(y.form,'.pluginConnectButton');this.buttons=z;this.node_connected=i.find(z,'.pluginConnectButtonConnected');this.node_disconnected=i.find(z,'.pluginConnectButtonDisconnected');var aa=function(ca){new k(ca).kill();this.submit();}.bind(this);w(this.node_disconnected,aa);w(i.find(z,'.pluginButtonX button'),aa);var ba=this.update.bind(this);v(o.CONNECT,ba,u);v(o.DISCONNECT,ba,u);v(o.ERROR,this.error.bind(this),u);v('Connect.Unsafe.xd/reposition',this.flip.bind(this));if(y.autosubmit)this.submit();}r(x.prototype,{update:function(y,event){var z=this.config;if(event.identifier!==z.identifier)return;var aa=y===o.CONNECT?true:false,ba="/plugins/"+z.plugin+"/";ba+=!aa?"connect":"disconnect";h[aa?'show':'hide'](this.node_connected);h[aa?'hide':'show'](this.node_disconnected);z.connected=aa;z.form.setAttribute('action',ba);z.form.setAttribute('ajaxify',ba);},error:function(event,y){if(y.action in {connect:1,disconnect:1})i.setContent(this.buttons,y.content);},submit:function(){if(!this.config.canpersonalize)return this.login();n.bootstrap(this.config.form);this.fireStateToggle();},fireStateToggle:function(){var y=this.config;if(y.connected){o.disconnect(y.identifier);}else o.connect(y.identifier);},login:function(){var y=this.config.plugin;new p(y).addReturnParams({act:'connect'}).start();},flip:function(y,z){var aa=i.find(document.body,'.pluginConnectButtonLayoutRoot');h.toggleClass(aa,"_4-nd");var ba=i.find(document.body,"._4xn8"),ca=i.scry(this.config.form,'.pluginConnectButtonConnected .pluginButtonIcon'),da=q.get(ca[0],'display')!=='none'?ca[0]:ca[1],ea=(z.type==='restore')?6:m.getElementPosition(da).x+j.getElementDimensions(da).width/2-6;q.set(ba,'left',ea+'px');}});e.exports=x;});
__d("UnverifiedXD",["XD","XDUnverifiedChannel"],function(a,b,c,d,e,f){var g=b('XD').UnverifiedXD,h=c('XDUnverifiedChannel').channel;g.init({channelUrl:h});e.exports=g;});
__d("PluginConnectButtonEvent",["Arbiter","Plugin","UnverifiedXD"],function(a,b,c,d,e,f){var g=b('Arbiter'),h=b('Plugin'),i=b('UnverifiedXD'),j={listen:function(k){g.subscribe([h.CONNECT,h.DISCONNECT],function(l,event){if(event.identifier!==k)return;if(typeof event.story_fbid!=='undefined'&&!event.crossFrame)i.send({type:l===h.CONNECT?'edgeCreated':'edgeRemoved'});},g.SUBSCRIBE_NEW);}};e.exports=j;});
__d("PluginConnection",["Arbiter","CSS","Plugin","copyProperties"],function(a,b,c,d,e,f){var g=b('Arbiter'),h=b('CSS'),i=b('Plugin'),j=b('copyProperties'),k=function(){};j(k.prototype,{init:function(l,m,n,event){event=event||i.CONNECT;this.identifier=l;this.element=m;this.css=n;g.subscribe([i.CONNECT,i.DISCONNECT],function(o,p){if(l===p.href)h[o===event?'addClass':'removeClass'](m,n);return true;});return this;},connected:function(){return h.hasClass(this.element,this.css);},connect:function(){return g.inform(i.CONNECT,{href:this.identifier},g.BEHAVIOR_STATE);},disconnect:function(){return g.inform(i.DISCONNECT,{href:this.identifier},g.BEHAVIOR_STATE);},toggle:function(){return this.connected()?this.disconnect():this.connect();}});k.init=function(l){for(var m,n=0;n<l.length;n++){m=new k();m.init.apply(m,l[n]);}};e.exports=k;});
__d("PluginOverflowItems",["CSS","Style","createArrayFrom"],function(a,b,c,d,e,f){var g=b('CSS'),h=b('Style'),i=b('createArrayFrom');function j(k,l){var m=k.parentNode.offsetHeight;i(k.parentNode.childNodes).forEach(function(o){if(o!==k)m-=o.offsetHeight;});k.style.height=m+'px';var n=0;i(k.getElementsByClassName(l)).forEach(function(o){g.show(o);n+=o.offsetHeight+parseInt(h.get(o,'margin-top'),10)+parseInt(h.get(o,'margin-bottom'),10);if(n>m)g.hide(o);});}j.adjust=function(k,l){new j(k,l);};e.exports=j;});
__d("PluginPerms",["DOMEvent","DOMEventListener","PluginMessage","PopupWindow","URI","copyProperties"],function(a,b,c,d,e,f){var g=b('DOMEvent'),h=b('DOMEventListener'),i=b('PluginMessage'),j=b('PopupWindow'),k=b('URI'),l=b('copyProperties');function m(n,o){l(this,{return_params:k.getRequestURI().getQueryData(),login_params:{},perms_params:{},perms:[],plugin:n,app:o});this.addReturnParams({ret:'perms'});delete this.return_params.hash;}l(m.prototype,{addReturnParams:function(n){l(this.return_params,n);},addLoginParams:function(n){l(this.login_params,n);},addPermsParams:function(n){l(this.perms_params,n);},addPerms:function(n){this.perms.push.apply(this.perms,n);},start:function(){var n=k('/dialog/plugin.perms').addQueryData(this.perms_params).addQueryData({display:'popup',app_id:this.app,perms:this.perms.join(','),secure:k.getRequestURI().isSecure(),social_plugin:this.plugin,return_params:JSON.stringify(this.return_params),login_params:JSON.stringify(this.login_params)});this.popup=j.open(n.toString(),210,450);i.listen();}});m.starter=function(n,o,p,q,r,s){var t=new m(n,o);t.addReturnParams(q||{});t.addLoginParams(r||{});t.addPermsParams(s||{});t.addPerms(p||[]);return t.start.bind(t);};m.listen=function(n,o,p,q,r,s,t){h.add(n,'click',function(u){new g(u).kill();m.starter(o,p,q,r,s,t)();});};e.exports=m;});
__d("PluginResize",["Log","UnverifiedXD","copyProperties"],function(a,b,c,d,e,f){var g=b('Log'),h=b('UnverifiedXD'),i=b('copyProperties');function j(m){m=m||document.body;return m.offsetWidth+m.offsetLeft;}function k(m){m=m||document.body;return m.offsetHeight+m.offsetTop;}function l(m,n,event,o){this.calcWidth=m||j;this.calcHeight=n||k;this.width=undefined;this.height=undefined;this.reposition=!!o;this.event=event||'resize';}i(l.prototype,{resize:function(){var m=this.calcWidth(),n=this.calcHeight();if(m!==this.width||n!==this.height){g.debug('Resizing Plugin: (%s, %s, %s, %s)',m,n,this.event,this.reposition);this.width=m;this.height=n;h.send({type:this.event,width:m,height:n,reposition:this.reposition});}return this;},auto:function(m){setInterval(this.resize.bind(this),m||250);return this;}});l.auto=function(m,event,n){return new l(j.bind(null,m),k.bind(null,m),event).resize().auto(n);};l.autoHeight=function(m,n,event,o){return new l(function(){return m;},k.bind(null,n),event).resize().auto(o);};e.exports=l;});
__d("PluginShareButton",["DOMEvent","DOMEventListener","PluginResize","PopupWindow","UserAgent"],function(a,b,c,d,e,f){var g=b('DOMEvent'),h=b('DOMEventListener'),i=b('PluginResize'),j=b('PopupWindow'),k=b('UserAgent'),l={listen:function(m,n){var o=m.href;h.add(m,'click',function(p){new g(p).kill();j.open(o,340,670);});},resize:function(m){var n=k.firefox()||k.ie()>=9?1:0;new i(function(){return m.offsetWidth+m.offsetLeft+n;},function(){return m.offsetHeight+m.offsetTop;}).resize().auto();}};e.exports=l;});
__d("PluginXDReady",["Arbiter","UnverifiedXD"],function(a,b,c,d,e,f){var g=b('Arbiter'),h=b('UnverifiedXD'),i={handleMessage:function(j){if(!j.method)return;try{g.inform('Connect.Unsafe.'+j.method,JSON.parse(j.params),g.BEHAVIOR_PERSISTENT);}catch(k){}}};a.XdArbiter=i;h.send({xd_action:'plugin_ready',name:window.name});e.exports=null;});
__d("PluginLinkshim",["LinkshimAsyncLink"],function(a,b,c,d,e,f){var g=b('LinkshimAsyncLink'),h={globalizeLegacySymbol:function(){a.LinkshimAsyncLink=g;}};e.exports=h;});