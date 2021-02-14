# Composer Stuffs
composer install

# Clear Current Build
rm -rf /var/www/app.redstonegang.com.br/builds/current/*

# Copy the new Build
cp -rf * /var/www/app.redstonegang.com.br/builds/current/

# Create Synbolic Links
ln -s /var/www/app.redstonegang.com.br/shared/.env /var/www/app.redstonegang.com.br/builds/current/.env

# Clear Others Stuffs
rm -rf /var/www/app.redstonegang.com.br/builds/current/README.md

# Laravel Stuffs
php /var/www/app.redstonegang.com.br/builds/current/artisan migrate