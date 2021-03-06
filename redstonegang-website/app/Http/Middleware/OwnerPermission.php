<?php

namespace App\Http\Middleware;

use Closure;
use Session;

class OwnerPermission
{
    /**
     * Handle an incoming request.
     *
     * @param \Illuminate\Http\Request $request
     * @param \Closure $next
     * @param $permission Permission, same used in java
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        $account = \Auth::user();
        if ($account == null) {
            Session::flash('previousLocation', $request->fullUrl());
            return redirect(route('login'));
        }
        if ($account->user != null && in_array($account->user->name, ['Feldmann', 'Mahzinha', 'net32', 'ForeverPlayerG'])) {
            return $next($request);
        }
        return abort(403);
    }
}
