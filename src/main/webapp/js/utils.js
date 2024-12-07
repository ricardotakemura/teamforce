const _s = {
	isBlank: function(str) {
		const aux = (str || "")
			.replace(/^\s/,"")
			.replace(/\s$/,"");
		return aux.length == 0;
	},
	anyBlanks: function(strs) {
		return strs.some(_s.isBlank);
	}
};