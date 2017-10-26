/**
 * author zhangxueliang
 * create date 2015-09-15
 *
 **/
$.extend($.fn.datagrid.methods, {
    autoMergeCells : function (jq, fields) {
        return jq.each(function () {
            var target = $(this);
            if (!fields) {
                fields = target.datagrid("getColumnFields");
            }
            var rows = target.datagrid("getRows");
            var i = 0,
            j = 0,
            temp = {};
            temps = [];
            for (i; i < rows.length; i++) {
                var row = rows[i];
                j = 0;
                for (j; j < fields.length; j++) {
                    var field = fields[j];
                    var tf = temp[field];
                    if (!tf) {
                        tf = temp[field] = {};
                        tf[row[field]] = [i];
                    } else {
                    	var x = true;
                        var tfv = tf[row[field]];
                        for (var k=0; k < fields.length; k++) {
                        	var ktf = temp[fields[k]];
							var f = ktf[row[fields[k]]];
							if(!f){
								x = false;
								$.each(temp, function (field, colunm) {
					                $.each(colunm, function () {
					                    var group = this;
					                    if (group.length > 1) {
					                        var before,
					                        after,
					                        megerIndex = group[0];
					                        for (var i = 0; i < group.length; i++) {
					                            before = group[i];
					                            after = group[i + 1];
					                            if (after && (after - before) == 1) {
					                                continue;
					                            }
					                            var rowspan = before - megerIndex + 1;
					                            if (rowspan > 1) {
					                                target.datagrid('mergeCells', {
					                                    index : megerIndex,
					                                    field : field,
					                                    rowspan : rowspan
					                                });
					                            }
					                            if (after && (after - before) != 1) {
					                                megerIndex = after;
					                            }
					                        }
					                    }
					                });
					            });
								temp={};
								i--;
								break;
							}
						}
                        if (x) {
                            tfv.push(i);
                        } else {
                            tfv = tf[row[field]] = [i];
                            break;
                        }                    }
                }
            }
            $.each(temp, function (field, colunm) {
                $.each(colunm, function () {
                    var group = this;
                    
                    if (group.length > 1) {
                        var before,
                        after,
                        megerIndex = group[0];
                        for (var i = 0; i < group.length; i++) {
                            before = group[i];
                            after = group[i + 1];
                            if (after && (after - before) == 1) {
                                continue;
                            }
                            var rowspan = before - megerIndex + 1;
                            if (rowspan > 1) {
                                target.datagrid('mergeCells', {
                                    index : megerIndex,
                                    field : field,
                                    rowspan : rowspan
                                });
                            }
                            if (after && (after - before) != 1) {
                                megerIndex = after;
                            }
                        }
                    }
                });
            });
        });
    }
});