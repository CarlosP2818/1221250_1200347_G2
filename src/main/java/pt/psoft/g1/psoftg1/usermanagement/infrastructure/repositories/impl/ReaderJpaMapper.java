package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.jpa.ReaderJpa;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.util.Optional;

@Component
public class ReaderJpaMapper {

    private static SpringDataUserRepository repo;
    private static SpringDataReaderRepository readerRepo;

    @Autowired
    public ReaderJpaMapper(SpringDataUserRepository injectedRepo, SpringDataReaderRepository injectedReaderRepo) {
        ReaderJpaMapper.repo = injectedRepo;
        ReaderJpaMapper.readerRepo = injectedReaderRepo;
    }
    public static Reader toDomain(ReaderJpa jpa) {
        if (jpa == null) return null;
        Reader reader = new Reader(jpa.getUsername(), jpa.getPassword());
        reader.setName(jpa.getName().getName());
        jpa.getAuthorities().forEach(reader::addAuthority);
        return reader;
    }

    public static ReaderJpa toJpa(Reader reader) {
        if (reader == null) return null;

        return readerRepo.findByUsername(reader.getUsername())
                .orElseGet(() -> {
                    ReaderJpa jpa = new ReaderJpa(reader.getUsername(), reader.getPassword());
                    jpa.setName(reader.getName().getName());
                    reader.getAuthorities().forEach(jpa::addAuthority);
                    return jpa;
                });
    }

}
