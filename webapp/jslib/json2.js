//用法
//var   obj=new   Object();
//obj.Name= 'Nams ';
//obj.Sex=1;
//JSON2.serialize(obj);//will   get:   {Name: 'Nams ',Sex:1}
if (typeof StringBuilder == 'undefined') {
	StringBuilder = function(initialText) {
		var _parts = new Array();
		if ((typeof(initialText) == 'string') && (initialText.length != 0)) _parts.push(initialText);

		this.append = function(text) {
			if ((text == null) || (typeof(text) == 'undefined')) {
				return;
			}
			if ((typeof(text) == 'string') && (text.length == 0)) {
				return;
			}
			_parts.push(text);
		}
		this.appendLine = function(text) {
			this.append(text);
			_parts.push('/r/n');
		}
		this.clear = function() {
			_parts.clear();
		}
		this.isEmpty = function() {
			return (_parts.length == 0);
		}
		this.toString = function(delimiter) {
			return _parts.join(delimiter || '');
		}
	}
}

String.prototype.startsWith = function(str){
	return (this.match("^"+str)==str)
}

JSON2 = new function() {
	function serializeWithBuilder(object, stringBuilder) {
		var i;

		switch (typeof object) {
		case 'object':
			if (object) {
				if (object.join) { // if is array,
					// you can using
					// another
					// method
					// implement
					stringBuilder.append('[');
					for (i = 0; i < object.length; ++i) {
						if (i > 0) {
							stringBuilder.append(',');
						}
						stringBuilder.append(serializeWithBuilder(object[i], stringBuilder));
					}
					stringBuilder.append(']');
				} else {
					if (typeof object.serialize == 'function') {
						stringBuilder.append(object.serialize());
						break;
					}
					stringBuilder.append('{');
					var needComma = false;
					for (var name in object) {
						if (name.startsWith('$')) {
							continue;
						}
						var value = object[name];
						if (typeof value != 'undefined' && typeof value != 'function') {
							if (needComma) stringBuilder.append(',');
							else needComma = true;

							stringBuilder.append(serializeWithBuilder(name, stringBuilder));
							stringBuilder.append(':');
							stringBuilder.append(serializeWithBuilder(value, stringBuilder));
						}
					}
					stringBuilder.append('}');
				}
			} else {
				stringBuilder.append('null');
			}
			break;

		case 'number':
			if (isFinite(object)) {
				stringBuilder.append(String(object));
			} else {
				stringBuilder.append('null');
			}
			break;

		case 'string':
			stringBuilder.append('"');
			var length = object.length;
			for (i = 0; i < length; ++i) {
				var curChar = object.charAt(i);
				if (curChar >='') {
					if (curChar == '//' || curChar == '"') {
						stringBuilder.append('//');
					}
					stringBuilder.append(curChar);
				} else {
					switch (curChar) {
					case '/b':
						stringBuilder.append('//b');
						break;
					case '/f':
						stringBuilder.append('//f');
						break;
					case '/n':
						stringBuilder.append('//n');
						break;
					case '/r':
						stringBuilder.append('//r');
						break;
					case '/t':
						stringBuilder.append('//t');
						break;
					default:
						stringBuilder.append('//u00');
						stringBuilder.append(curChar.charCodeAt().toString(16));
					}
				}
			}
			stringBuilder.append('"');
			break;

		case 'boolean':
			stringBuilder.append(object.toString());
			break;

		default:
			stringBuilder.append('null');
			break;
		}
	}

	this.serialize = function(object) {
		var stringBuilder = new StringBuilder();
		serializeWithBuilder(object, stringBuilder);
		return stringBuilder.toString();
	}

	this.deserialize = function(data) {
		return eval('(' + data + ')');
	}
}

var isSupportJSON;
var winJSON = null;
if(window.JSON){
	winJSON = window.JSON;
	isSupportJSON = true;
}else{
	isSupportJSON = false;
}


var JSON = null;
if(isSupportJSON){
	JSON = winJSON;
}else{
	JSON = {
			stringify : function(obj){
				return JSON2.serialize(obj);
			}
	};
}