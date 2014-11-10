<!DOCTYPE html>
<html>
	<head>

		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js" ></script>
		<script src="lib/TRGeneration.js" ></script>
		
	</head>
	<body>
		
		<div id="wrapper" style="margin:20px;width:100%;" >
			<table style="width:100%;">
				<tr>
					<td style="width:60%;vertical-align:top;">
						<form id="srcform" autocomplete="off">
							<textarea name="src_code" id="src_code" rows=30 style="width:100%;"></textarea>
							<div style="width:100%;text-align:center">
								<input type="button" name="submit_button" id="submit_button" value="Submit" onclick="submit_source();"/>
							</div>
						</form>
					</td>
					<td rowspan=2 align="center">
						<div id="png_output"></div>
					</td>
				</tr>	
				<tr>
					<td style="vertical-align:top;">
						<div id="tr_output" style="width:100%;"></div>	
					</td>
				</tr>
			</table>
		</div>
		
	</body>
</html>