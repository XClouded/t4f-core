var XWiki=(function(b){var a=b.widgets=b.widgets||{};
a.ModalPopup=Class.create({options:{globalDialog:true,title:"",displayCloseButton:true,screenColor:"",borderColor:"",titleColor:"",backgroundColor:"",screenOpacity:"0.5",verticalPosition:"center",horizontalPosition:"center",removeOnClose:false,onClose:Prototype.emptyFunction},initialize:function(e,c,d){this.shortcuts={show:{method:this.showDialog,keys:["Ctrl+G","Meta+G"]},close:{method:this.closeDialog,keys:["Esc"]}},this.content=e||"Hello world!";
this.shortcuts=Object.extend(Object.clone(this.shortcuts),c||{});
this.options=Object.extend(Object.clone(this.options),d||{});
this.registerShortcuts("show")
},createDialog:function(e){this.dialog=new Element("div",{"class":"xdialog-modal-container"});
var d=new Element("div",{"class":"xdialog-screen"}).setStyle({opacity:this.options.screenOpacity,backgroundColor:this.options.screenColor});
this.dialog.update(d);
this.dialogBox=new Element("div",{"class":"xdialog-box"});
this.dialogBox._x_contentPlug=new Element("div",{"class":"xdialog-content"});
this.dialogBox.update(this.dialogBox._x_contentPlug);
this.dialogBox._x_contentPlug.update(this.content);
if(this.options.title){var f=new Element("div",{"class":"xdialog-title"}).update(this.options.title);
f.setStyle({color:this.options.titleColor});
this.dialogBox.insertBefore(f,this.dialogBox.firstChild)
}if(this.options.displayCloseButton){var c=new Element("div",{"class":"xdialog-close",title:"Close"}).update("&#215;");
c.observe("click",this.closeDialog.bindAsEventListener(this));
if(this.options.title){f.insert({bottom:c});
if(this.options.titleColor){c.setStyle({color:this.options.titleColor})
}}else{this.dialogBox.insertBefore(c,this.dialogBox.firstChild)
}}this.dialog.appendChild(this.dialogBox);
this.dialogBox.setStyle({textAlign:"left",borderColor:this.options.borderColor,backgroundColor:this.options.backgroundColor});
switch(this.options.verticalPosition){case"top":this.dialogBox.setStyle({top:"0"});
break;
case"bottom":this.dialogBox.setStyle({bottom:"0"});
break;
default:this.dialogBox.setStyle({top:"35%"});
break
}switch(this.options.horizontalPosition){case"left":this.dialog.setStyle({textAlign:"left"});
break;
case"right":this.dialog.setStyle({textAlign:"right"});
break;
default:this.dialog.setStyle({textAlign:"center"});
this.dialogBox.setStyle({margin:"auto"});
break
}document.body.appendChild(this.dialog);
this.dialog.hide()
},setClass:function(c){this.dialogBox.addClassName("xdialog-box-"+c)
},removeClass:function(c){this.dialogBox.removeClassName("xdialog-box-"+c)
},setContent:function(c){this.content=c;
this.dialogBox._x_contentPlug.update(this.content)
},showDialog:function(c){if(c){Event.stop(c)
}if(this.options.globalDialog){if(a.ModalPopup.active){return
}else{a.ModalPopup.active=true
}}else{if(this.active){return
}else{this.active=true
}}if(!this.dialog){this.createDialog()
}this.attachKeyListeners();
this.dialog.show()
},closeDialog:function(c){if(c){Event.stop(c)
}this.options.onClose.call(this);
this.dialog.hide();
if(this.options.removeOnClose){this.dialog.remove()
}this.detachKeyListeners();
if(this.options.globalDialog){a.ModalPopup.active=false
}else{this.active=false
}},attachKeyListeners:function(){for(var c in this.shortcuts){if(c!="show"){this.registerShortcuts(c)
}}},detachKeyListeners:function(){for(var c in this.shortcuts){if(c!="show"){this.unregisterShortcuts(c)
}}},registerShortcuts:function(e){var c=this.shortcuts[e].keys;
var f=this.shortcuts[e].method;
for(var d=0;
d<c.size();
++d){if(Prototype.Browser.IE||Prototype.Browser.WebKit){shortcut.add(c[d],f.bindAsEventListener(this,e),{type:"keyup"})
}else{shortcut.add(c[d],f.bindAsEventListener(this,e),{type:"keypress"})
}}},unregisterShortcuts:function(d){for(var c=0;
c<this.shortcuts[d].keys.size();
++c){shortcut.remove(this.shortcuts[d].keys[c])
}},createButton:function(d,f,e,h){var g=new Element("span",{"class":"buttonwrapper"});
var c=new Element("input",{type:d,"class":"button",value:f,title:e,id:h});
g.update(c);
return g
}});
a.ModalPopup.active=false;
return b
}(XWiki||{}));