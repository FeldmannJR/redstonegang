<?php

// @formatter:off
/**
 * A helper file for your Eloquent Models
 * Copy the phpDocs from this file to the correct Model,
 * And remove them from this file, to prevent double declarations.
 *
 * @author Barry vd. Heuvel <barryvdh@gmail.com>
 */


namespace App{
/**
 * App\Skin
 *
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Skin newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Skin newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Skin query()
 */
	class Skin extends \Eloquent {}
}

namespace App{
/**
 * App\ServerConfig
 *
 * @method static \Illuminate\Database\Eloquent\Builder|\App\ServerConfig newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\ServerConfig newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\ServerConfig query()
 */
	class ServerConfig extends \Eloquent {}
}

namespace App\Servers{
/**
 * App\Servers\Server
 *
 * @property-read \App\Servers\Machine $machine
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Servers\Server newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Servers\Server newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Servers\Server query()
 */
	class Server extends \Eloquent {}
}

namespace App\Servers{
/**
 * App\Servers\ApiToken
 *
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Servers\ApiToken newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Servers\ApiToken newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Servers\ApiToken query()
 */
	class ApiToken extends \Eloquent {}
}

namespace App\Servers{
/**
 * App\Servers\Machine
 *
 * @property-read \Illuminate\Database\Eloquent\Collection|\App\Servers\Server[] $servers
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Servers\Machine newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Servers\Machine newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Servers\Machine query()
 */
	class Machine extends \Eloquent {}
}

namespace App{
/**
 * App\RegisterToken
 *
 * @property-read \App\Models\Common\User $user
 * @method static \Illuminate\Database\Eloquent\Builder|\App\RegisterToken newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\RegisterToken newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\RegisterToken query()
 */
	class RegisterToken extends \Eloquent {}
}

namespace App\Models\Common{
/**
 * App\Models\Common\PermissionDesc
 *
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\PermissionDesc newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\PermissionDesc newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\PermissionDesc query()
 */
	class PermissionDesc extends \Eloquent {}
}

namespace App\Models\Common{
/**
 * App\Models\Common\Group
 *
 * @property-read \App\Models\XF\XFUserGroup $forum
 * @property-read \Illuminate\Database\Eloquent\Collection|\App\Models\Common\GroupOption[] $options
 * @property-read \App\Models\Common\Group $parentGroup
 * @property-read \Illuminate\Database\Eloquent\Collection|\App\Models\Common\Permission[] $permissions
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\Group newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\Group newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\Group query()
 */
	class Group extends \Eloquent {}
}

namespace App\Models\Common{
/**
 * App\Models\Common\Permission
 *
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\Permission newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\Permission newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\Permission query()
 */
	class Permission extends \Eloquent {}
}

namespace App\Models\Common{
/**
 * App\Models\Common\User
 *
 * @property-read \App\Account $account
 * @property-read \App\Models\Common\Group $parentGroup
 * @property-read \Illuminate\Database\Eloquent\Collection|\App\Models\Common\Permission[] $permissions
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\User newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\User newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\Common\User query()
 */
	class User extends \Eloquent {}
}

namespace App\Models\XF{
/**
 * App\Models\XF\XFUser
 *
 * @property-read \App\Account $account
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\XF\XFUser newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\XF\XFUser newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\XF\XFUser query()
 */
	class XFUser extends \Eloquent {}
}

namespace App\Models\XF{
/**
 * App\Models\XF\XFModel
 *
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\XF\XFModel newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\XF\XFModel newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\XF\XFModel query()
 */
	class XFModel extends \Eloquent {}
}

namespace App\Models\XF{
/**
 * App\Models\XF\XFUserGroup
 *
 * @property-read \Illuminate\Database\Eloquent\Collection|\App\Models\Common\Group[] $groups
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\XF\XFUserGroup newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\XF\XFUserGroup newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Models\XF\XFUserGroup query()
 */
	class XFUserGroup extends \Eloquent {}
}

namespace App{
/**
 * App\Map
 *
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Map newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Map newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Map query()
 */
	class Map extends \Eloquent {}
}

namespace App{
/**
 * App\Account
 *
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Account newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Account newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Account query()
 * @mixin \Eloquent
 * @property-read \App\Models\XF\XFUser $forum
 * @property-read \App\Models\Common\User $user
 */
	class Account extends \Eloquent {}
}

namespace App{
/**
 * App\AccountKitSession
 *
 * @property-read \App\Models\Common\User $user
 * @method static \Illuminate\Database\Eloquent\Builder|\App\AccountKitSession newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\AccountKitSession newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\AccountKitSession query()
 */
	class AccountKitSession extends \Eloquent {}
}

