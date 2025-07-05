<?php
header('Content-Type: application/json');

// Initialize random helpers
function randFloat($min, $max) {
    return $min + mt_rand() / mt_getrandmax() * ($max - $min);
}

// Initial increments
$inc1 = 0.1 * 2;
$inc2 = 0.05 * 2;
$inc3 = $inc1 * 50;
$inc4 = $inc2 * 50;

// Random seeds
$ran  = randFloat(0, 1);
$ran2 = randFloat(0, 1);
$ran3 = randFloat(0, 1);
$ran4 = randFloat(0, 1);
$ran5 = randFloat(0, 1);

// Time and base readings
$temp = 20;
$hum = 87 * (0.5 + ($ran - 0.5));
$hour = ceil($ran * 24);
$minute = ceil($ran2 * 60);
$day = ceil($ran3 * 31);
$month = ceil($ran4 * 12);
$year = ceil($ran5 * 4500);

// Temp Model
if ($hour > 10 && $hour < 18) {
    if (in_array($month, [1,2]))     $temp = 7;
    if (in_array($month, [5,8]))     $temp = 18;
    if (in_array($month, [4,9]))     $temp = 14;
    if (in_array($month, [3,10]))    $temp = 15;
    if (in_array($month, [2,11]))    $temp = 10;
    if (in_array($month, [1,12]))    $temp = 6;
} elseif ($hour < 10 || $hour > 18) {
    if (in_array($month, [6,7]))     $temp = 14;
    if (in_array($month, [5,8]))     $temp = 12;
    if (in_array($month, [4,9]))     $temp = 9;
    if (in_array($month, [3,10]))    $temp = 5;
    if (in_array($month, [2,11]))    $temp = 3;
    if (in_array($month, [1,12]))    $temp = 2;
}

// Long-term climate model
if ($year > -1 && $year <1000) $temp -= 2;
if ($year > 1000 && $year <1300) $temp -= 4;
if ($year > 1300 && $year <1700) $temp -= 2;
if ($year > 1600 && $year <1700) $temp -= 3;
if ($year > 1700 && $year <1750) $temp -= 6;
if ($year > 1750 && $year <1920) $temp -= 5;
if ($year > 1920 && $year <1950) $temp -= 4;
if ($year > 1950 && $year <1980) $temp -= 3;
if ($year > 1980 && $year <2000) $temp -= 2;
if ($year > 2040 && $year <2100) $temp += 2;
if ($year > 2100 && $year <2200) $temp += 4;
if ($year > 2200 && $year <2300) $temp += 6;
if ($year > 2300 && $year <2400) $temp += 7;
if ($year > 2400 && $year <2600) $temp += 6;
if ($year > 2600 && $year <3100) $temp += 5;
if ($year > 3100 && $year <4000) $temp += 3;
if ($year > 4000 && $year <4500) $temp -= 1;

// Dynamic changes (1 cycle simulation)
$inc1 = $inc1/10 * (1 + (-0.5 + randFloat(0, 1)));
$inc2 = $inc2/10 * (1 + (-0.5 + randFloat(0, 1)));

if ($hour > 9 && $hour < 14) {
    $temp += $inc1;
    $hum -= $inc1 * 20;
}
elseif (($hour > 7 && $hour < 9) || ($hour > 14 && $hour < 16)) {
    $temp += $inc2;
    $hum -= $inc2 * 20;
}
elseif ($hour > 16 && $hour < 22) {
    $temp -= $inc2 / 2;
    $hum += $inc2 * 20;
}
elseif (($hour > 0 && $hour < 7) || $hour > 22) {
    $temp -= $inc1;
    $hum -= $inc1 * 20;
}

// Wind simulation
$wspeed1 = randFloat(0, 200);
$wspeed2 = randFloat(0, 200);
$UPPER_BOUND = 27;
$LOWER_BOUND = 7;
$quickwind = 10;

if ($wspeed1 < $UPPER_BOUND && $wspeed1 > $LOWER_BOUND &&
    $wspeed2 < $UPPER_BOUND && $wspeed2 > $LOWER_BOUND &&
    ($wspeed1 / $wspeed2 <= 0.985)) {
    $filter = $wspeed2;
    if ($wspeed1 / $filter <= 0.985) {
        $quickwind = $wspeed1;
    }
}

// Pressure
$pressure = 1023 * (1 + (-0.5 + randFloat(0, 1)));
$pressure = max(977, min(1029, $pressure));
$inc3 = $inc3 * (1 + (-0.5 + randFloat(0, 1)) * 3);
$inc4 = $inc4 * (1 + (-0.5 + randFloat(0, 1)) * 3);

if ($hour > 9 && $hour < 14) $pressure += $inc3;
elseif (($hour > 7 && $hour < 9) || ($hour > 14 && $hour < 16)) $pressure += $inc4;
elseif ($hour > 16 && $hour < 22) $pressure -= $inc4 / 2;
elseif (($hour >= 0 && $hour < 7) || $hour > 22) $pressure -= $inc3;

if ($quickwind > 30) $pressure -= 0.01;

// Final output
echo json_encode([
    "temp" => round($temp, 1),
    "hum" => round($hum, 1),
    "hour" => $hour,
    "minute" => $minute,
    "day" => $day,
    "month" => $month,
    "year" => $year,
    "quickwind" => round($quickwind, 1),
    "pressure" => round($pressure, 1)
]);
