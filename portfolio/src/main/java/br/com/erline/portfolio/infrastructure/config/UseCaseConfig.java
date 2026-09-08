package br.com.erline.portfolio.infrastructure.config;

import br.com.erline.portfolio.application.usecase.AprovarDepoimentoUseCase;
import br.com.erline.portfolio.application.usecase.CriarDepoimentoUseCase;
import br.com.erline.portfolio.application.security.SenhaHasher;
import br.com.erline.portfolio.application.usecase.CriarUsuarioUseCase;
import br.com.erline.portfolio.application.usecase.ListarDepoimentosUseCase;
import br.com.erline.portfolio.application.usecase.RejeitarDepoimentoUseCase;
import br.com.erline.portfolio.domain.repository.DepoimentoRepository;
import br.com.erline.portfolio.domain.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CriarDepoimentoUseCase criarDepoimentoUseCase(
            DepoimentoRepository repository
    ) {
        return new CriarDepoimentoUseCase(repository);
    }

    @Bean
    public ListarDepoimentosUseCase listarDepoimentosUseCase(
            DepoimentoRepository repository
    ) {
        return new ListarDepoimentosUseCase(repository);
    }

    @Bean
    public AprovarDepoimentoUseCase aprovarDepoimentoUseCase(
            DepoimentoRepository repository
    ) {
        return new AprovarDepoimentoUseCase(repository);
    }

    @Bean
    public RejeitarDepoimentoUseCase rejeitarDepoimentoUseCase(
            DepoimentoRepository repository
    ) {
        return new RejeitarDepoimentoUseCase(repository);
    }

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase(
            UsuarioRepository repository,
            SenhaHasher senhaHasher
    ) {
        return new CriarUsuarioUseCase(repository, senhaHasher);
    }
}