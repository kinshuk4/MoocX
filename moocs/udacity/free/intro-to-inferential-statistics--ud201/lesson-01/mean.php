<?php

/***
	取平均數的常態分佈
***/
error_reporting(E_ERROR);


$sample_count = $argv[1] ? $argv[1] : 10000;
$sample_size = $argv[2] ? $argv[2] : 5;

$score_list = file_get_contents("./klout.txt");

$score_list = explode("\n", $score_list);

$size = count($score_list);

$avg_arr = [];

$max = 0;
$min = 10000;

for($i = 0 ; $i < $sample_count; $i ++){
	$total = 0;
	for($j = 0 ; $j < $sample_size; $j ++){
	    $idx = rand(0, $size -1);

	    $total += $score_list[$idx];

	    if($score_list[$idx] > $max){
	    	$max = $score_list[$idx];
	    }

	    if($score_list[$idx] < $min){
	    	$min = $score_list[$idx];
	    }
	}

	$avg = $total / $sample_size;

	$avg_arr[floor($avg)] ++;
}

ksort($avg_arr);

foreach($avg_arr as $idx => $val){
	echo "{$idx} , {$val}\n"; 
}

echo "sample size: {$sample_size}\nsample count: {$sample_count} \nmax:{$max} min:{$min}\n";