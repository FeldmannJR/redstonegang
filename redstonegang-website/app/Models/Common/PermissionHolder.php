<?php

namespace App\Models\Common;

use Illuminate\Database\Eloquent\Model;

abstract class PermissionHolder extends Model
{

    public function isStaff()
    {
        return $this->hasPermission('redstonegang.staff');
    }

    public function hasPermission($permission, PermissionServer $server = null)
    {
        if ($server === null) {
            $server = PermissionServer::GERAL();
        }

        // Verifica se tem no server especificado
        $perm = $this->getPermissionValue($permission, $server);
        if ($perm != PermissionValue::NONE) {
            return $perm === PermissionValue::ALLOW;
        }
        // Caso não tiver procura no geral
        if ($server !== PermissionServer::GERAL) {
            $perm = $this->getPermissionValue($permission, $server);
            if ($perm != PermissionValue::NONE) {
                return $perm === PermissionValue::ALLOW;
            }
        }

        // Se não tiver geral manda pro parent
        $parent = $this->parentGroup;
        if ($parent === null) {
            if ($this->useDefaultParent()) {
                $parent = $this->getDefault();
                if ($parent !== null && $parent->id === $this->id) {
                    $parent = null;
                }
            }
        }
        if ($parent !== null) {
            return $parent->hasPermission($permission, $server);
        }

        return false;
    }

    private function getDefault(): Group
    {
        return Group::where('default', true)->first();
    }

    public function setPermission($permission, PermissionValue $value, PermissionServer $server = null)
    {
        if ($server === null) {
            $server = PermissionServer::GERAL();
        }

        $perm = Permission::firstOrNew([
            'owner' => $this->getKey(),
            'key' => $permission,
            'type' => $this->getMorphClass(),
            'server' => $server->value
        ]);
        $perm->value = $value->value;
        $perm->save();
    }

    private function getPermissionValue($permission, PermissionServer $server)
    {
        $perms = $this->permissions;
        foreach ($perms as $perm) {
            if ($perm->key === '*') {
                return PermissionValue::ALLOW;
            }
            if ($perm->server == $server->value && $perm->key == $permission) {
                return $perm->value->value;
            }
        }
        return PermissionValue::NONE;
    }

    public function permissionsToArray(PermissionServer $server)
    {
        $group_permissions = $this->permissions()->select('key', 'value')->where('server', $server->value)->get();
        $permissions = [];
        foreach ($group_permissions as $perm) {
            $permissions[$perm->key] = $perm->value->value;
        }
        return $permissions;
    }

    protected abstract function getParentFieldName();

    protected abstract function useDefaultParent(): bool;

    public function parentGroup()
    {
        return $this->belongsTo(Group::class, $this->getParentFieldName());
    }

    public function permissions()
    {
        return $this->morphMany(Permission::class, 'type', 'type', 'owner', 'id');
    }
}
