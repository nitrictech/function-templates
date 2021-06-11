<?php

namespace App\Function;

use Nitric\V1\Faas\Faas;


$handler = function(Request $request): Response
{
	return new Response("Nitric PHP 8 Template - Success");
}

Faas::start($handler);