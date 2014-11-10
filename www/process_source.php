<?php

if (!isset($_POST['src_code'])){
        echo 'Missing source code';
        return false;
}

$srcfile = fopen(__DIR__."/tmp/source.java", "w");
fwrite($srcfile, $_POST['src_code']); //at least try to be a little secure
fclose($srcfile);

$output = array();
exec("java -jar ".__DIR__."/bin/TRGeneration.jar ".__DIR__."/tmp/source.java ".__DIR__."/output/out.png",$output);

$res = array();
$res['tr'] = "<p>".implode("</p><p>",$output)."</p>";
$res['png'] = 'out.png';

unlink(__DIR__."/tmp/source.java");

echo json_encode($res);

?>
