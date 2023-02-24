<?php

use App\Http\Controllers\JwtController;
use Illuminate\Support\Facades\Route;
use App\Models\Persones;
use App\Http\Controllers\MarquesController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return response()->json(true);
});


Route::get('/api/get/marques', [MarquesController::class, 'index']);

Route::get('/persones', function () {
    $persones = Persones::all();
    return response()->json($persones);
});

Route::get('/persones/{id}', function ($id) {
    $persones = Persones::find($id);
    return response()->json($persones, 200, ['Content-Type' => 'application/json;charset=UTF-8', 'Charset' => 'utf-8']);
});
 
 
/* Route::group(['middleware' => ['cors','tokenMiddleware']], function ()  
{ 
    Route::get('/persones', function () {  
        $persones = Persones::all();
        return response()->json($persones);
    }); 
}); */