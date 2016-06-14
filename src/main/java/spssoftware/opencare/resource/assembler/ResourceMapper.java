package spssoftware.opencare.resource.assembler;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Service;

@Service
public class ResourceMapper extends ConfigurableMapper {

    private boolean springInjected = false;

//    @Autowired
//    public ResourceMapper(AuthorUriConverter authorUriConverter) {
//        this.authorUriConverter = authorUriConverter;
//        springInjected = true;
//        init();
//    }


    @Override
    protected void configure(MapperFactory factory) {
        super.configure(factory);
    }
}
