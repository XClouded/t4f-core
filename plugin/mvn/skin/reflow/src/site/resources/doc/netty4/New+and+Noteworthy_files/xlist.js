var XWiki=function(b){var a=b.widgets=b.widgets||{};
a.XList=Class.create({initialize:function(c,d){this.items=c||[];
this.options=d||{};
this.listElement=new Element(this.options.ordered?"ol":"ul",{"class":"xlist"+(this.options.classes?(" "+this.options.classes):"")});
if(this.items&&this.items.length>0){for(var e=0;
e<this.items.length;
e++){this.addItem(this.items[e])
}}},addItem:function(c){if(!c||!(c instanceof b.widgets.XListItem)){c=new b.widgets.XListItem(c)
}var d=c.getElement();
if(this.options.itemClasses&&!this.options.itemClasses.blank()){d.addClassName(this.options.itemClasses)
}this.listElement.insert(d);
if(typeof this.options.eventListeners=="object"){c.bindEventListeners(this.options.eventListeners)
}if(this.options.icon&&!this.options.icon.blank()){c.setIcon(this.options.icon,this.options.overrideItemIcon)
}c.list=this
},getElement:function(){return this.listElement
}});
a.XListItem=Class.create({initialize:function(e,c){this.options=c||{};
var d="xitem "+(this.options.noHighlight?"":"xhighlight ");
d+=this.options.classes?this.options.classes:"";
this.containerElement=new Element("div",{"class":"xitemcontainer"}).insert(e||"");
this.containerElement.addClassName(this.options.containerClasses||"");
this.containerElement.setStyle({textIndent:"0px"});
if(this.options.value){this.containerElement.insert(new Element("div",{"class":"hidden value"}).insert(this.options.value))
}this.listItemElement=new Element("li",{"class":d}).update(this.containerElement);
if(this.options.icon&&!this.options.icon.blank()){this.setIcon(this.options.icon);
this.hasIcon=true
}if(typeof this.options.eventListeners=="object"){this.bindEventListeners(this.options.eventListeners)
}},getElement:function(){return this.listItemElement
},setIcon:function(d,c){if(!this.hasIcon||c){this.iconImage=new Image();
this.iconImage.onload=function(){this.listItemElement.setStyle({backgroundImage:"url("+this.iconImage.src+")",backgroundRepeat:"no-repeat",backgroundPosition:"3px 3px"});
this.listItemElement.down(".xitemcontainer").setStyle({textIndent:(this.iconImage.width+6)+"px"})
}.bind(this);
this.iconImage.src=d
}},bindEventListeners:function(e){var d=Object.keys(e);
for(var c=0;
c<d.length;
c++){this.listItemElement.observe(d[c],e[d[c]].bindAsEventListener(this.options.eventCallbackScope?this.options.eventCallbackScope:this))
}}});
return b
}(XWiki||{});