  var _mN = _mN || {};
var iframeURL = 'http://contextual.media.net/mediamain.html?cid=8CUVGNQ2X&crid=781787909&pid=8POSHQ3J3&size=160x600&cpnet=yVb1sHm%2B0KIh29BOFTjjrIcZyfhpUaYvRxI4%2FZs1h0I%3D&cme=%2BGrnje2DVSUEOXxUGC8xNiavel%2FfZ5XsSFK0AfXs0QesujLe0BGWzw%3D%3D%7C%7CN7fu2vKt8%2Fs%3D%7CFcl4VLL%2BIaKXgw3Ff0QyJIqFbVRXcPSyjunvSjyANKw%3D%7CJf0d%2BWoAdPtYUw5Cq7Ahx9B0WL15zFfkNmO%2Bs03pZMk%3D%7C&cc=BE&bf=0&vif=1';
var locHash = '';
try{
	if(typeof _mNDetails != 'undefined' && _mNDetails['locHash'] && _mNDetails['locHash']['781787909']){ locHash += _mNDetails['locHash']['781787909'];};
	if(locHash != ''){
		locHash = '#'+locHash;
	}
	locHash += '&dytm=' + new Date().getTime();
}catch(e){}
iframeURL += locHash;
_mNDetails = (typeof _mNDetails != "undefined")?_mNDetails:{};
_mNDetails['locHash'] = _mNDetails['locHash'] || {};		
_mNDetails['locHash']['781787909'] = locHash;
var _mN_mc_cnt = _mN_mc_cnt || 0; 
var _mN_mc_frameID = '_mN_main_781787909'+'_'+_mN_mc_cnt++;
_mNDetails = (typeof _mNDetails != "undefined")?_mNDetails:{};
_mNDetails['iframeURL'] = _mNDetails['iframeURL'] || {};
_mNDetails['iframeURL']['781787909'] = iframeURL; 
document.write("<iframe marginwidth='0' marginheight='0' scrolling='NO' frameborder='0' height='600' width='160' id='"+_mN_mc_frameID+"' style='display:none;' ></iframe>");var _mN_mainCont = '<!DOCTYPE html><html><head><scr'+'ipt type="text/javascript">var frameID, callback = false, mFrame = window.parent._mNDetails["iframeURL"]["781787909"].replace(/#.*/,"");function createTag() {var scr = document.createElement("script");scr.onload = scr.onreadystatechange = function () { if(typeof adContent != "undefined" && !callback) { callback = true; parent._mN_dy.putContent(frameID, adContent.content, "160", "600", "", "0"); } }; scr.type = "text/javascript"; scr.src = mFrame+\'&nb=1\'; scr.async = "async"; document.getElementsByTagName("head")[0].appendChild(scr);};</scr'+'ipt></head><body onload="createTag()"></body></html>';
var _mN_dy = _mN_dy || {};
_mN_dy.eventLib = {
	addEvent: function (elem, type, eventHandle) {
		if(elem.addEventListener) {
			elem.addEventListener( type, eventHandle, false );
		} else if ( elem.attachEvent ) {
			elem.attachEvent( "on" + type, eventHandle );
		}
		elem = null; // Handle Memory Leak
	},
	removeEvent: function(elem, type, eventHandle) {
		if (elem.removeEventListener) {
			elem.removeEventListener(type, eventHandle);
		} else if(elem.detachEvent) {
			elem.detachEvent('on' + type, eventHandle);
		}
		elem = null;
	}
};
_mN_dy.getContent = function (data, url, w, h, id, insl) {
	var ifr = document.getElementById(id),
	errload = function(){_mN_dy.getContent(data, url, w, h, id);};
	
	if(!ifr){
		_mN_dy.eventLib.addEvent(document, 'DOMContentLoaded', errload);  
		//_mN_dy.eventLib.addEvent(window, 'load', errload);
		ifr = null;
		return;
	}
	//_mN_dy.eventLib.removeEvent(window, 'load', errload);
	var doc,
	win = ifr.contentWindow || ifr.contentDocument; 
	try { 
		doc = (win && (win.document || win)) || false;
		if(doc) {
			doc.open();
			win.frameID = id;
			doc.write(data);
			doc.close();
			win.frameID = id;
			//win.locHash = window.locHash || null;
		}
	} catch (e) { 
		_mN_dy.putContent(id, '', w, h, url, insl);
	}
}
_mN_dy.putContent = function (id, data, w, h, url, insl) {
	var ifr = document.getElementById(id), 
	mainFrame = document.createElement("iframe");
	mainFrame.marginWidth = 0;
	mainFrame.marginHeight = 0;
	mainFrame.scrolling = "no";
	mainFrame.frameBorder = 0;
	mainFrame.height = h;
	mainFrame.width = w;
	mainFrame.id = id+"_n";
	if(mainFrame.attachEvent) { mainFrame.attachEvent('onload', (function (mf) { return function() { _mN._mNVI.trigger(mf); };})(mainFrame)); } else { mainFrame.onload = function (){ var doc = this.contentWindow.document; if(doc && doc.body && doc.body.firstChild) { _mN._mNVI.trigger(this); } } }
	if(url != '' && data == ''){
		mainFrame.src = url;
	}
	ifr.parentNode.insertBefore(mainFrame, ifr);
	if(url != '' && data == ''){
		mainFrame.parentNode.removeChild(ifr);
		return;
	}
	
	var win = mainFrame.contentWindow || mainFrame.contentDocument,
	doc = (win && (win.document || win)) || false;
	if(doc) { 
		try {
			if(/msie/.test(navigator.userAgent.toLowerCase())){
				throw 10;
			}
			doc.open();
			doc.write(data);
			doc.close();
			//win.locHash = window.locHash || null;
		} catch (e) { 
			win.data = data;
			doc.location.replace('javascript:window["data"];');
		}
		if(insl && insl == '1'){
			_mN_dy.inttAd && _mN_dy.inttAd.init && _mN_dy.inttAd.init(mainFrame);
		}
		mainFrame.parentNode.removeChild(ifr);
	}
};_mN_dy.getContent(_mN_mainCont, iframeURL, '160', '600', _mN_mc_frameID, '0');