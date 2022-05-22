package sopt.sopterm.sannumsan.repository;

import java.util.ArrayList;
import java.util.List;
import sopt.sopterm.sannumsan.domain.Mountain;

public abstract class MountainRepositoryImpl implements MountainRepository {

    private List<Mountain> mountainList = new ArrayList<>();

    @Override
    public List<Mountain> findAll() {
        return mountainList;
    }

    @Override
    public List<Mountain> findAllByIdIn(List<Long> idList) {
        return mountainList;
    }

    @Override
    public List<Long> getIdList(Long count) {
        List<Long> idList = new ArrayList<Long>();
        while (idList.size() < 5) {
            Long newId = (long) (Math.random() * count) + 1;
            if (idList.indexOf(newId) == -1) {
                idList.add(newId);
            }
        }
        return idList;
    }
}
