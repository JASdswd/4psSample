<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

 <!-- <link href="style.css" rel="stylesheet" type="text/css" /> -->
    <script src="jquery-1.2.6.min.js" type="text/javascript"></script>

  <script src="jquery.tablesorter-2.0.3.js" type="text/javascript"></script> 
    <script src="jquery.tablesorter.filer.js" type="text/javascript"></script>
    <script src="jquery.tablesorter.pager.js" type="text/javascript"></script>


 <script type="text/javascript">
        $(document).ready(function() {
     
            $("#tableOne").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                        .tablesorterPager({ container: $("#pagerOne"), positionFixed: false //})
                        /* .tablesorterFilter({ filterContainer: $("#filterBoxOne"),
                         filterClearContainer: $("#filterClearOne"),
                         filterColumns: [0, 1, 2, 3, 4, 5, 6],
                         filterCaseSensitive: false */
             });
        });    
        
             
    </script>       


</head>
<body>
<table>
	<tr>
		<td>
			teofelma
		</td>
	</tr>
	
</table>
<table id="tableOne" >    
	<thead><!-- 
        <tr>

            <td class="tableHeader">
                Students
            </td>
            <td colspan="8" class="filter" style="border-right: solid 3px #7f7f7f;">
                Search: <input id="filterBoxOne" value="" maxlength="30" size="30" type="text" />
                <img id="filterClearOne" src="cross.png" title="Click to clear filter." alt="Clear Filter Image" />
            </td>
        </tr>  -->	
		<tr>

			<th><a href='#' title="Click Header to Sort">Name</a></th>
			<th><a href='#' title="Click Header to Sort">Major</a></th>
			<th><a href='#' title="Click Header to Sort">Sex</a></th>
			<th><a href='#' title="Click Header to Sort">English</a></th>
			<th><a href='#' title="Click Header to Sort">Japanese</a></th>
			<th><a href='#' title="Click Header to Sort">Calculus</a></th>

			<th style="border-right: solid 3px #7f7f7f"><a href='#' title="Click Header to Sort">Geometry</a></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Student01</td>
			<td>Languages</td>

			<td>M</td>
			<td>80</td>
			<td>70</td>
			<td>75</td>
			<td style="border-right: solid 3px #7f7f7f;">80</td>
		</tr>

		<tr>

			<td>Student02</td>
			<td>Mathematics</td>
			<td>M</td>
			<td>90</td>
			<td>88</td>

			<td>100</td>
			<td style="border-right: solid 3px #7f7f7f;">90</td>
		</tr>
		<tr>
			<td>Student03</td>
			<td>Languages</td>
			<td>F</td>

			<td>85</td>
			<td>95</td>
			<td>80</td>
			<td style="border-right: solid 3px #7f7f7f;">85</td>
		</tr>
		<tr>
			<td>Student04</td>

			<td>Languages</td>
			<td>M</td>
			<td>60</td>
			<td>55</td>
			<td>100</td>
			<td style="border-right: solid 3px #7f7f7f;">100</td>

		</tr>
		<tr>
			<td>Student05</td>
			<td>Languages</td>
			<td>F</td>
			<td>68</td>
			<td>80</td>

			<td>95</td>
			<td style="border-right: solid 3px #7f7f7f;">80</td>
		</tr>
		<tr>
			<td>Student06</td>
			<td>Mathematics</td>
			<td>M</td>

			<td>100</td>
			<td>99</td>
			<td>100</td>
			<td style="border-right: solid 3px #7f7f7f;">90</td>
		</tr>
		<tr>
			<td>Student07</td>

			<td>Mathematics</td>
			<td>M</td>
			<td>85</td>
			<td>68</td>
			<td>90</td>
			<td style="border-right: solid 3px #7f7f7f;">90</td>

		</tr>
		<tr>
			<td>Student08</td>
			<td>Languages</td>
			<td>M</td>
			<td>100</td>
			<td>90</td>

			<td>90</td>
			<td style="border-right: solid 3px #7f7f7f;">85</td>
		</tr>
		<tr>
			<td>Student09</td>
			<td>Mathematics</td>
			<td>M</td>

			<td>80</td>
			<td>50</td>
			<td>65</td>
			<td style="border-right: solid 3px #7f7f7f;">75</td>
		</tr>
		<tr>
			<td>Student10</td>

			<td>Languages</td>
			<td>M</td>
			<td>85</td>
			<td>100</td>
			<td>100</td>
			<td style="border-right: solid 3px #7f7f7f;">90</td>

		</tr>
		<tr>
			<td>Student11</td>
			<td>Languages</td>
			<td>M</td>
			<td>86</td>
			<td>85</td>

			<td>100</td>
			<td style="border-right: solid 3px #7f7f7f;">100</td>
		</tr>
		<tr>
			<td>Student12</td>
			<td>Mathematics</td>
			<td>F</td>

			<td>100</td>
			<td>75</td>
			<td>70</td>
			<td style="border-right: solid 3px #7f7f7f;">85</td>
		</tr>
		<tr>
			<td>Student13</td>

			<td>Languages</td>
			<td>F</td>
			<td>100</td>
			<td>80</td>
			<td>100</td>
			<td style="border-right: solid 3px #7f7f7f;">90</td>

		</tr>
		<tr>
			<td>Student14</td>
			<td>Languages</td>
			<td>F</td>
			<td>50</td>
			<td>45</td>

			<td>55</td>
			<td style="border-right: solid 3px #7f7f7f;">90</td>
		</tr>
		<tr>
			<td>Student15</td>
			<td>Languages</td>
			<td>M</td>

			<td>95</td>
			<td>35</td>
			<td>100</td>
			<td style="border-right: solid 3px #7f7f7f;">90</td>
		</tr>
	<tfoot>
	    <tr id="pagerOne">
	        <td colspan="7" style="border-right: solid 3px #7f7f7f;">
		        <img src="first.png" class="first"/>

		        <img src="prev.png" class="prev"/>
		        <input type="text" class="pagedisplay"/>
		        <img src="next.png" class="next"/>
		        <img src="last.png" class="last"/>
		        <select class="pagesize">
			        <option selected="selected"  value="10">10</option>

			        <option value="5">5</option>

			        <option value="15">15</option>
			        <option  value="20">20</option>
		        </select>
		    </td>
	    </tr>
	</tfoot>
</table>

</body>
</html>