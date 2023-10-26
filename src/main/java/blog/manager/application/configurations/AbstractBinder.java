package blog.manager.application.configurations;

import blog.manager.application.services.BasicHTTPAuthenticationService;
import blog.manager.application.services.CustomAuthorizationService;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import blog.manager.domain.model.UserRepository;
import blog.manager.domain.model.album.AlbumRepository;
import blog.manager.domain.model.bild.BildRepository;
import blog.manager.domain.model.blogeintrag.BlogeintragRepository;
import blog.manager.domain.model.chefredakteur.ChefredakteurRepository;
import blog.manager.domain.model.kommentar.KommentarRepository;
import blog.manager.domain.model.nutzer.NutzerRepository;
import blog.manager.domain.model.produktrezension.ProduktrezensionRepository;
import blog.manager.domain.model.redakteur.RedakteurRepository;
import blog.manager.domain.model.schalgwort.SchlagwortRepository;
import blog.manager.infrastructure.repositories.*;

import javax.sql.DataSource;
import java.io.File;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;

public class AbstractBinder extends org.glassfish.hk2.utilities.binding.AbstractBinder {
    @Override
    protected void configure() {
        bind(getDataSource()).to(DataSource.class);
        bind(SQLiteUserRepository.class).to(UserRepository.class);
        bind(SQLiteNutzerRepository.class).to(NutzerRepository.class);
        bind(SQLiteRedakteurRepository.class).to(RedakteurRepository.class);
        bind(SQLiteChefredakteurRepository.class).to(ChefredakteurRepository.class);
        bind(SQLiteBlogeintragRepository.class).to(BlogeintragRepository.class);
        bind(SQLiteSchlagwortRepository.class).to(SchlagwortRepository.class);
        bind(SQLiteAlbumRepository.class).to(AlbumRepository.class);
        bind(SQLiteBildRepository.class).to(BildRepository.class);
        bind(SQLiteKommentarRepository.class).to(KommentarRepository.class);
        bind(SQLiteProduktrezensionRepository.class).to(ProduktrezensionRepository.class);
        bindAsContract(BasicHTTPAuthenticationService.class);
        bindAsContract(CustomAuthorizationService.class);
    }

    private SQLiteDataSource getDataSource() {
        Properties properties = new Properties();
        properties.put("auto_vacuum", "full");
        SQLiteConfig config = new SQLiteConfig(properties);
        config.setEncoding(SQLiteConfig.Encoding.UTF8);
        config.enforceForeignKeys(true);
        config.setJournalMode(SQLiteConfig.JournalMode.WAL);
        config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        try {
            dataSource.setLogWriter(new PrintWriter(System.out));
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        dataSource.setUrl("jdbc:sqlite:data" + File.separator + "database.db");
        return dataSource;
    }
}
