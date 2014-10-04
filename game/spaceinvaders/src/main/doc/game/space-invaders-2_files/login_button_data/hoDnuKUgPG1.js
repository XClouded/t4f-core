/*1364400400,178142491*/

if (self.CavalryLogger) { CavalryLogger.start_js(["GuVrA"]); }

__d("ScubaSample",["Banzai","copyProperties"],function(a,b,c,d,e,f){var g="scuba_sample",h=b("Banzai"),i=b("copyProperties");function j(m,n,o){this.fields={};this.flush=function(){if(!m)return;var p={};i(p,this.fields);p._ds=m;if(n)p._lid=n;p._options=o;h.post(g,p);this.flush=function(){};this.flushed=true;};this.lid=n;return this;}function k(m,n,o){if(!this.fields[m])this.fields[m]={};this.fields[m][n]=o;return this;}function l(m){return function(n,o){if(this.flushed)return this;return k.call(this,m,n,o);};}i(j.prototype,{addNormal:l('normal'),addInteger:l('int'),addDenorm:l('denorm')});e.exports=j;});
__d("ScrollPerfLogger",["Arbiter","Event","Run","ScriptPath","ScubaSample","SubscriptionsHandler","UserAgent","requestAnimationFrame"],function(a,b,c,d,e,f){var g=b('Arbiter'),h=b('Event'),i=b('Run'),j=b('ScriptPath'),k=b('ScubaSample'),l=b('SubscriptionsHandler'),m=b('UserAgent'),n=b('requestAnimationFrame'),o;function p(){if(o!==undefined)return o;var ga=window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.oRequestAnimationFrame||window.msRequestAnimationFrame;return (o=!!ga);}function q(){if(w[0]>ca.max_frames){t();y=!ca.continuous;if(y){ba&&ba.release();ba=null;}else{u();n(q);}return;}if(y)return;var ga=Date.now();if(ga-z<ca.max_idle_time)for(var ha in w)if(ga-aa>ha)w[ha]++;aa=ga;n(q);}var r;function s(){if(typeof r!=='undefined')return r;if(m.chrome()){r='Chrome '+m.chrome();}else if(m.webkit()){r='WebKit '+m.webkit();}else if(m.firefox()){r='Firefox '+m.firefox();}else if(m.ie()){r='IE '+m.ie();}else if(m.opera()){r='Opera '+m.opera();}else r=null;return r;}function t(){if(y||w[0]<ca.min_frames)return;var ga=new k('scroll',null,{addBrowserFields:true});ga.addNormal('experiment',x);ga.addNormal('time_bucket',v());ga.addNormal('script_path',j.getScriptPath());if(s())ga.addNormal('browser_name_and_version',s());for(var ha in da)ga.addNormal(ha,da[ha]);var ia={};for(var ja in w){if(ja===0)continue;ia[ja]=Math.round(w[ja]/w[0]*ca.count_ratio);}for(ja in ia){if(ja===0)continue;ga.addInteger('freeze'+ja,ia[ja]);}ga.addInteger('total_frames',w[0]);ga.flush();}function u(){w={0:0,50:0,100:0,200:0,500:0,1000:0};aa=Date.now();}function v(){var ga=Date.now()-ea;if(ga<=300000){return '0 - 5m';}else if(ga<=600000){return '5 - 10m';}else if(ga<=1800000){return '10 - 30m';}else if(ga<=3600000){return '30 - 60m';}else return '1+ h';}var w,x='',y,z=0,aa=0,ba,ca,da,ea,fa={init:function(ga,ha,ia){if(ha&&ha.must_support_raf&&!p())return;x=ga;ca=ha;da=ia||{};ea=Date.now();y=false;ba=new l();ba.addSubscriptions(h.listen(window,'scroll',function(){z=Date.now();}));u();n(q);i.onLeave(function(){t();y=true;ba.release();ba=null;});}};e.exports=fa;});
__d("ScriptMonitorReporter",["ScriptMonitor","ScubaSample","setTimeoutAcrossTransitions","URI"],function(a,b,c,d,e,f){var g=b('ScriptMonitor'),h=b('ScubaSample'),i=b('setTimeoutAcrossTransitions'),j=b('URI');function k(o){var p=[];for(var q=0;q<o.length;q++)p.push(new RegExp(o[q],'i'));return p;}function l(o,p){for(var q=0;q<p.length;q++)if(p[q].src)o.push(p[q].src);}function m(o,p){for(var q=0;q<p.length;q++)if(p[q].test(o))return true;return false;}function n(o,p){var q=g.stop(),r={addGeoFields:1,addBrowserFields:1,addUser:1},s={};l(q,document.getElementsByTagName('script'));l(q,document.getElementsByTagName('iframe'));for(var t=0;t<q.length;t++){var u=q[t].replace(/\?.*/,'');if(s[u])continue;s[u]=1;if(!m(u,p)&&!m(new j(u).getDomain(),o))new h('unknown_scripts',0,r).addNormal('url',u).flush();}}e.exports={runScan:function(o,p){i(function(){n(k(o),k(p));},5000);}};});