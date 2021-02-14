<?php
function array_map_assoc(callable $f, array $a)
{
    $newArray = [];
    foreach ($a as $key => $value) {
        $new = $f($key, $value);
        if ($new === null || !is_array($new)) {
            continue;
        }
        foreach ($new as $newkey => $newvalue) {
            $newArray[$newkey] = $newvalue;
        }
    }
    return $newArray;
}