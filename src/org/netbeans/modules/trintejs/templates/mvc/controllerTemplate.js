/**
 *  <?= controllerName ?> Controller
 *
 *  @package     <?= pack ?>
 *  @version     <?= version ?>
 *  @desc        <?= description ?>
 *  @author      <?= author ?>
 *  @created     <?= created ?>
 *
 *  Created by create-controller script
 *  App based on TrinteJS MVC framework
 *  TrinteJS homepage http://www.trintejs.com
 **/
<?
  var modelVariable = modelName.toLowerCase();
  var basePath = modelName.toLowerCase();
  var basePathPlural = controllerName.toLowerCase();
  var addIncl = namespace ? "../" : "" ;
  var addView = namespace ? namespace + "/" : "" ;
  var nsPath = namespace ? namespace + "_" : "" ;
?>
var pager = require('<?= addIncl ?>../lib/pager.js'),
ViewTemplatePath = '<?= addView ?><?= basePathPlural ?>';

module.exports = {

    /**
     * Index action, returns a list either via the <?= addView ?>views/<?= basePathPlural ?>/index.ejs view or via json
     * Default mapping to GET '<?= namespace ?>/<?= basePathPlural ?>'
     * For JSON use '<?= namespace ?>/<?= basePathPlural ?>.json'
     * View Helper method pathTo.<?= nsPath ?><?= basePathPlural ?>()
     * For paging use  pathTo.paging_<?= nsPath ?><?= basePathPlural ?>(from, to)
     * @param {Object} req
     * @param {Object} res
     * @param {Function} next
     **/
    'index': function(req, res, next) {
        var title = "<?= controllerName ?>";
        var sort = req.query.sort;
        var from = req.params.from ? parseInt(req.params.from) - 1 : 0;
        var to = req.params.to ? parseInt(req.params.to) : 20;
        var total = 0;
        var where = {
            skip : from,
            limit : to,
            order : 'id DESC'
        };

        if(sort && sort !== '') {
            var direction = 'ASC';
            if(sort.substr(0,1) === '-') {
                direction = 'DESC';
                sort = sort.substr(1,sort.length);
            }
            where.order = sort + " " + direction;
        }

        <?= modelName ?>.count({}, function (err, count) {
        total = count;
        var pagerHtml = pager.render(from, to, total, pathTo.<?= nsPath ?><?= basePathPlural ?>());

        <?= modelName ?>.all(where, function (err, <?= basePathPlural ?>) {

                if(err) return next(err);

                switch (req.params.format) {
                    case 'json':
                        res.send(<?= basePathPlural ?>.map(function(u) {
                            return u.toObject();
                        }));
                        break;
                    default:
                        res.render(ViewTemplatePath,{<?= basePathPlural ?>:<?= basePathPlural ?>,pagerHtml:pagerHtml,title:title});
                }
            });
        });
    },

    /**
     * Show action, returns shows a single item via <?= addView ?>views/<?= basePathPlural ?>/show.ejs view or via json
     * Default mapping to GET '<?= namespace ?>/<?= basePath ?>/:id'
     * For JSON use '<?= namespace ?>/<?= basePath ?>/:id.json'
     * View Helper method pathTo.show_<?= nsPath ?><?= modelVariable ?>(id)
     * @param {Object} req
     * @param {Object} res
     * @param {Function} next
     **/
    'show': function(req, res, next) {
            var title = "Show <?= modelName ?>";
            <?= modelName ?>.find(req.params.id, function(err, <?= modelVariable ?>) {

            if(err) return next(err);

            switch (req.params.format) {
                case 'json':
                    res.send(<?= modelVariable ?>.toObject());
                    break;

                default:
                    res.render(ViewTemplatePath + "/show",{<?= modelVariable ?>:<?= modelVariable ?>,title:title});
            }
        });
    },

    /**
     * New action, returns a form via <?= addView ?>views/<?= basePathPlural ?>/edit.ejs view no JSON view.
     * Default mapping to GET '<?= namespace ?>/<?= basePath ?>/new'
     * View Helper method pathTo.new_<?= nsPath ?><?= modelVariable ?>()
     * @param {Object} req
     * @param {Object} res
     * @param {Function} next
     **/
    'new': function(req, res, next) {
        var title = "New <?= modelName ?>";
        var <?= modelVariable ?> = new <?= modelName ?>();
        res.render(ViewTemplatePath + "/new",{<?= modelVariable ?>:<?= modelVariable ?>,title:title});
    },

    /**
     * Edit action, returns a form via <?= addView ?>views/<?= basePathPlural ?>/edit.ejs view no JSON view.
     * Default mapping to GET '<?= namespace ?>/<?= basePath ?>/:id/edit'
     * View Helper method pathTo.edit_<?= nsPath ?><?= modelVariable ?>(id)
     * @param {Object} req
     * @param {Object} res
     * @param {Function} next
     **/
    'edit': function(req, res, next) {
            var title = "Edit <?= modelName ?>";
            <?= modelName ?>.find(req.params.id, function(err, <?= modelVariable ?>) {
            if(err) return next(err);
            res.render(ViewTemplatePath + "/edit",{<?= modelVariable ?>:<?= modelVariable ?>,title:title});
        });
    },

    /**
     * Update action, updates a single item and redirects to Show or returns the object as json
     * Default mapping to PUT '<?= namespace ?>/<?= basePath ?>/:id', no GET mapping
     * View Helper method pathTo.update_<?= nsPath ?><?= modelVariable ?>(id)
     * @param {Object} req
     * @param {Object} res
     * @param {Function} next
     **/
    'update': function(req, res, next) {
        <?= modelName ?>.find(req.params.id, function(err, <?= modelVariable ?>) {
            <?= modelVariable ?>.updateAttributes(req.body.<?= modelVariable ?>, function(err) {

                if (err) {
                    console.log(err);
                    req.flash('error','Could not update <?= modelVariable ?>: ' + err);
                    res.redirect(pathTo.edit_<?= nsPath ?><?= modelVariable ?>(<?= modelVariable ?>.id));
                    return;
                }

                switch (req.params.format) {
                    case 'json':
                        res.send(<?= modelVariable ?>.toObject());
                        break;
                    default:
                        req.flash('info', '<?= modelName ?> updated');
                        res.redirect(pathTo.show_<?= nsPath ?><?= modelVariable ?>(req.params.id));
                }
            });
        });
    },

    /**
     * Create action, creates a single item and redirects to Show or returns the object as json
     * Default mapping to POST '<?= namespace ?>/<?= basePathPlural ?>', no GET mapping
     * View Helper method pathTo.create_<?= nsPath ?><?= modelVariable ?>()
     * @param {Object} req
     * @param {Object} res
     * @param {Function} next
     **/
    'create': function(req, res, next){

        var <?= modelVariable ?> = new <?= modelName ?>(req.body.<?= modelVariable ?>);

            <?= modelVariable ?>.save(function(err) {

            if (err) {
                req.flash('error','Could not create <?= modelVariable ?>: ' + err);
                res.redirect(pathTo.<?= nsPath ?><?= basePathPlural ?>());
                return;
            }

            switch (req.params.format) {
                case 'json':
                    res.send(<?= modelVariable ?>.toObject());
                    break;
                default:
                    req.flash('info','<?= modelName ?> created');
                    res.redirect(pathTo.show_<?= nsPath ?><?= modelVariable ?>(<?= modelVariable ?>.id));
            }
        });
    },

    /**
     * Delete action, deletes a single item and redirects to index
     * Default mapping to DEL '<?= namespace ?>/<?= basePath ?>/:id', no GET mapping
     * View Helper method pathTo.destroy_<?= nsPath ?><?= modelVariable ?>(id)
     * @param {Object} req
     * @param {Object} res
     * @param {Function} next
     **/
    'destroy': function(req, res, next){

            <?= modelName ?>.find(req.params.id, function(err, <?= modelVariable ?>) {

            if (!<?= modelVariable ?>) {
                req.flash('error','Unable to locate the <?= modelVariable ?> to delete!');
                res.send('false');
                return false;
            };

                <?= modelVariable ?>.destroy(function(err) {
                if(err) {
                    req.flash('error','There was an error deleting the <?= modelVariable ?>!');
                    res.send('"' + pathTo.<?= nsPath ?><?= basePathPlural ?>() + '"');
                } else {
                    req.flash('info','<?= modelName ?> deleted');
                    res.send('"' + pathTo.<?= nsPath ?><?= basePathPlural ?>() + '"');
                }
            });
        });
    },

    /**
     * Delete action, deletes a all items and redirects to index
     * Default mapping to DEL '<?= namespace ?>/<?= basePathPlural ?>', no GET mapping
     * View Helper method pathTo.destroy_<?= nsPath ?><?= basePathPlural ?>()
     * @param {Object} req
     * @param {Object} res
     * @param {Function} next
     **/
    'destroyall': function(req, res, next){
        <?= modelVariable ?>.destroyAll(function(err) {
            if(err) {
                req.flash('error','There was an error deleting the <?= basePathPlural ?>!');
                res.send('"' + pathTo.<?= nsPath ?><?= basePathPlural ?>() + '"');
            } else {
                req.flash('info','<?= controllerName ?> deleted');
                res.send('"' + pathTo.<?= nsPath ?><?= basePathPlural ?>() + '"');
            }
        });
    }
};