<?php

namespace App\Function;

require_once __DIR__ . '/vendor/autoload.php';

use Nitric\Faas\Request;
use Nitric\Faas\Faas;

$handler = function(Request $request): string
{
	return "Nitric PHP 8 Template - Success";
}

Faas::start($handler);