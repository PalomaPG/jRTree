package dto;

import dto.AbstractDTO;

public interface IDtoConvertible {
    public <T extends AbstractDTO> T toDTO();
}