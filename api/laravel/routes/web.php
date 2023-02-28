<?php

use App\Http\Controllers\ArticlesController;
use App\Http\Controllers\CategoriaController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\MarquesController;
use App\Http\Controllers\PersonesController;

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

Route::controller(MarquesController::class)->group(function () {
    Route::get('/api/get/marques', 'index');
    Route::post('/api/create/marques', 'create');
    Route::post('/api/update/marques', 'update');
    Route::post('/api/delete/marques', 'delete');
    // TODO: Hacer papelera para recuperar eliminados
});

Route::controller(RolesController::class)->group(function () {
    Route::get('/api/get/roles', 'index');
});

Route::controller(ArticlesController::class)->group(function () {
    Route::get('/api/get/articles', 'index');
    Route::post('/api/create/articles', 'create');
    Route::post('/api/update/articles', 'update');
    Route::post('/api/delete/articles', 'delete');
});

Route::controller(PersonesController::class)->group(function () {
    Route::get('/api/get/persones', 'index');
    Route::post('/api/create/persones', 'create');
    Route::post('/api/update/persones', 'update');
    Route::post('/api/delete/persones', 'delete');
});

Route::controller(CategoriaController::class)->group(function () {
    Route::get('/api/get/categories', 'index');
    Route::get('/api/get/subcategories/{id}', 'getSubcategoriaByCategoriaId');
    Route::post('/api/create/categories', 'createCategory');
    Route::post('/api/create/subcategories', 'createSubcategory');
    Route::post('/api/update/categories', 'updateCategory');
    Route::post('/api/update/subcategories', 'updateSubcategory');
    Route::post('/api/delete/categories', 'deleteCategory');
    Route::post('/api/delete/subcategories', 'deleteSubcategory');
});
