<?php

namespace App\Http\Middleware;

use Closure;
use Exception;
use Illuminate\Http\Request;
use Firebase\JWT\JWT;
use Firebase\JWT\Key;

class TokenMiddleware
{
    public function handle(Request $request, Closure $next)
    {

        $authHeader = $request->header('Authorization');
        if (!$authHeader) {
            return response(['message' => 'Authorization header not found' . $authHeader], 401);
        }
        $token = str_replace('Bearer ', '', $authHeader);
        $secret = env('JWT_SECRET');
        try {
            $decoded = JWT::decode($token, new Key($secret, 'HS256'));

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
