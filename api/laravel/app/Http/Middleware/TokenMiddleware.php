<?php

namespace App\Http\Middleware;

use Exception;
use Closure;
use Firebase\JWT\JWT;
use Illuminate\Http\Request;

class TokenMiddleware
{
    public function handle(Request $request, Closure $next)
    {  
        
        $authHeader = $request->header('Authorization');  
        if (!$authHeader) {
            return response(['message' => 'Authorization header not found'. $authHeader], 401);
        }
        try {
            $token = str_replace('Bearer ', '', $authHeader); 
            $decoded = JWT::decode($token, env("JWT_SECRET"), array('HS256'));
            $roles = $decoded->rols;
            if (in_array("ADMINISTRADOR", $roles)) {
                return $next($request);
            }
        } catch (Exception $e) {
            return response()->json(['error' => 'Token inválido'], 401);
        }  
        return response()->json(['error' => 'No se proporcionó el token'], 401);
    }
}
