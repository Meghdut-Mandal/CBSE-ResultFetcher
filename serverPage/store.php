<?php
 
 $contents="";
    $contents .= $_GET["textArea"];
 
// save locally in public_html folder

file_put_contents("filename.txt", date("Y/m/d")." at ".date("h:i:sa").":-\n".$contents."\n\n\n", FILE_APPEND);
header("https://meghdutwindows.000webhostapp.com");

/* Make sure that code below does not get executed when we redirect. */
exit;
?>