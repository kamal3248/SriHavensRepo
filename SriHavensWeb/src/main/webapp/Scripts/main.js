/*
 *  jQuery table2excel - v1.1.2
 *  jQuery plugin to export an .xls file in browser from an HTML table
 *  https://github.com/rainabba/jquery-table2excel
 *
 *  Made by rainabba
 *  Under MIT License
 */
//table2excel.js
$(document).ready(function() {


	$.fn.excelinit = function(tableID) {
		var tableHTML = $('#'+tableID)[0].outerHTML;
		console.log(tableHTML);
		var fileName = 'download.xls';
		 var utf8Heading = "<meta http-equiv=\"content-type\" content=\"application/vnd.ms-excel; charset=UTF-8\">";
          var template = {
                head: "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\">" + utf8Heading + "<head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets>",
                sheet: {
                    head: "<x:ExcelWorksheet><x:Name>",
                    tail: "</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet>"
                },
                mid: "</x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body>",
                table: {
                    head: "<table>",
                    tail: "</table>"
                },
                foot: "</body></html>"
            };
        var fullTemplate= template.head;
        fullTemplate += template.sheet.head + tableHTML+ template.sheet.tail;
		var msie = window.navigator.userAgent.indexOf("MSIE ");
		if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
			dummyFrame.document.open('txt/html', 'replace');
			dummyFrame.document.write(tableHTML);
			dummyFrame.document.close();
			dummyFrame.focus();
			return dummyFrame.document.execCommand('SaveAs', true, fileName);
		}
		else {
			var a = document.createElement('a');
			tableHTML = tableHTML.replace(/  /g, '').replace(/ /g, '%20');
			a.href = 'data:application/vnd.ms-excel,' + fullTemplate;
			a.setAttribute('download', fileName);
			document.body.appendChild(a);
			a.click();
			document.body.removeChild(a);
		}
	};
})