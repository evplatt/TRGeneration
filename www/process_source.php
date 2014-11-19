<?php

if (!isset($_POST['src_code']) || $_POST['src_code'] == ''){
        echo 'Missing source code';
        return false;
}

$srcfile = fopen(__DIR__."/tmp/source.java", "w");
fwrite($srcfile, $_POST['src_code']); //at least try to be a little secure
fclose($srcfile);

$debug = "";
if (isset($_POST['debug']) && $_POST['debug'] == 1) $debug = "-d ";

$output = array();
exec("java -jar ".__DIR__."/bin/TRGeneration.jar ".$debug."-o ".__DIR__."/output/out.png ".__DIR__."/tmp/source.java 2>&1",$output);

$res = array();

if ($debug != "") $res['tr'] = implode('<br/>',$output);
else $res['tr'] = "<p>".implode("</p><p>",$output)."</p>";

$res['png'] = 'out.png';

//unlink(__DIR__."/tmp/source.java");

echo json_encode($res);

?>
